package com.example.student.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.student.config.InvoicesMapper;
import com.example.student.config.PaymentsMapper;
import com.example.student.dto.InvoicesRequestDTO;
import com.example.student.dto.InvoicesResponseDTO;
import com.example.student.dto.PaymentsRequestDTO;
import com.example.student.dto.PaymentsResponseDTO;
import com.example.student.entity.InvoicesEntity;
import com.example.student.entity.InvoicesStatus;
import com.example.student.entity.PaymentMethod;
import com.example.student.entity.PaymentsEntity;
import com.example.student.entity.StudentEntity;

import com.example.student.repository.InvoicesRespository;
import com.example.student.repository.PaymentsRepository;
import com.example.student.repository.StudentRepository;


import org.springframework.transaction.annotation.Transactional; 
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoicesService {
    private final InvoicesRespository invoicesRepository;
    private final StudentRepository studentRepo;
    private final InvoicesMapper mapper;
    private final PaymentsRepository paymentRepository;
    private final PaymentsMapper paymentMapper;

    public InvoicesResponseDTO createInvoice(InvoicesRequestDTO dto) {
   
        StudentEntity student = studentRepo.findById(dto.getStudent_id())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên để tạo hóa đơn"));
        InvoicesEntity invoice = mapper.toEntity(dto);
        invoice.setId(student);
        
        InvoicesEntity savedInvoice = invoicesRepository.save(invoice);
        return mapper.toResponse(savedInvoice);
    }
    public List<InvoicesResponseDTO> getInvoicesByStudent(Long studentId) {
        List<InvoicesEntity> invoices = invoicesRepository.findByStudentId(studentId);
        return invoices.stream()
                .map(mapper::toResponse)
                .toList();
    }
    @Transactional(rollbackFor = Exception.class) // Đảm bảo tính nguyên tử (Atomicity)
    public InvoicesResponseDTO payInvoice(Long invoiceId, PaymentsRequestDTO request) {
        
        InvoicesEntity invoice = invoicesRepository.findByIdWithLock(invoiceId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn ID: " + invoiceId));

        if (invoice.getStatus() == InvoicesStatus.PAID) {
            throw new RuntimeException("Hóa đơn đã thanh toán xong, không thể nộp thêm.");
        }

        double currentPaid = invoice.getAmount_paid() != null ? invoice.getAmount_paid() : 0.0;
        double newTotalPaid = currentPaid + request.getAmount();

        if (newTotalPaid > invoice.getAmount_total()) {
            throw new RuntimeException("LỖI: Số tiền nộp (" + request.getAmount() + 
                ") vượt quá dư nợ còn lại (" + (invoice.getAmount_total() - currentPaid) + ").");
        }

     
        invoice.setAmount_paid(newTotalPaid);
        
      
        if (newTotalPaid >= invoice.getAmount_total()) {
            invoice.setStatus(InvoicesStatus.PAID);
        } else {
            invoice.setStatus(InvoicesStatus.PARTIALLY_PAID);
        }

        
    PaymentsEntity payment = new PaymentsEntity();
    payment.setInvoice_id(invoice);
    payment.setAmount(request.getAmount());
    payment.setMethod(request.getMethod() != null ? request.getMethod() : PaymentMethod.CASH);
    
    paymentRepository.save(payment);   
        
    return mapper.toResponse(invoicesRepository.save(invoice));
    }

    public List<PaymentsResponseDTO> getPaymentHistory(Long invoiceId) {
    // Kiểm tra xem hóa đơn có tồn tại không trước khi tìm lịch sử
    if (!invoicesRepository.existsById(invoiceId)) {
        throw new RuntimeException("Không tìm thấy hóa đơn ID: " + invoiceId);
    }

    List<PaymentsEntity> payments = paymentRepository.findByInvoiceId(invoiceId);

    return payments.stream()
            .map(paymentMapper::toResponse) // Giả sử bạn đã có PaymentMapper
            .toList();
    }
   
}
