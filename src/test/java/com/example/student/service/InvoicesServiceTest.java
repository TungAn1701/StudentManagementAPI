package com.example.student.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import com.example.student.config.InvoicesMapper;
import com.example.student.config.PaymentsMapper;
import com.example.student.dto.InvoicesResponseDTO;
import com.example.student.dto.PaymentsRequestDTO;
import com.example.student.entity.InvoicesEntity;
import com.example.student.entity.InvoicesStatus;
import com.example.student.entity.PaymentMethod;
import com.example.student.repository.InvoicesRespository;
import com.example.student.repository.PaymentsRepository;
import com.example.student.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InvoicesServiceTest {

    @Mock private InvoicesRespository invoicesRepository;
    @Mock private StudentRepository studentRepo;
    @Mock private InvoicesMapper mapper;
    @Mock private PaymentsRepository paymentRepository;
    @Mock private PaymentsMapper paymentMapper;

    @InjectMocks private InvoicesService invoicesService;

    @Test
    void payInvoice_Success_PartiallyPaid() {
        // 1. Arrange
        Long invoiceId = 1L;
        PaymentsRequestDTO request = new PaymentsRequestDTO();
        request.setAmount(200.0);
        request.setMethod(PaymentMethod.CASH);
        
        InvoicesEntity invoice = new InvoicesEntity();
        invoice.setAmount_total(1000.0);
        invoice.setAmount_paid(100.0);
        invoice.setStatus(InvoicesStatus.PARTIALLY_PAID);

        when(invoicesRepository.findByIdWithLock(invoiceId)).thenReturn(Optional.of(invoice));
        when(invoicesRepository.save(any())).thenReturn(invoice);
        when(mapper.toResponse(any())).thenReturn(new InvoicesResponseDTO());

        // 2. Act
        InvoicesResponseDTO result = invoicesService.payInvoice(invoiceId, request);

        // 3. Assert
        assertEquals(300.0, invoice.getAmount_paid());
        assertEquals(InvoicesStatus.PARTIALLY_PAID, invoice.getStatus());
        verify(paymentRepository, times(1)).save(any());
    }

    @Test
    void payInvoice_Fail_InvoiceAlreadyPaid() {
        // 1. Arrange
        Long invoiceId = 1L;
        PaymentsRequestDTO request = new PaymentsRequestDTO();
        request.setAmount(200.0);
        request.setMethod(PaymentMethod.CASH);
        
        InvoicesEntity invoice = new InvoicesEntity();
        invoice.setStatus(InvoicesStatus.PAID); // Đã thanh toán xong

        when(invoicesRepository.findByIdWithLock(invoiceId)).thenReturn(Optional.of(invoice));

        // 2 & 3. Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            invoicesService.payInvoice(invoiceId, request);
        });

        assertEquals("Hóa đơn đã thanh toán xong, không thể nộp thêm.", exception.getMessage());
        verify(paymentRepository, never()).save(any());  
        }

    @Test
    void payInvoice_Fail_OverPaid() {
        // 1. Arrange
        Long invoiceId = 1L;
        PaymentsRequestDTO request = new PaymentsRequestDTO();
        request.setAmount(600.0); 
        request.setMethod(PaymentMethod.CASH);

        InvoicesEntity invoice = new InvoicesEntity();
        invoice.setAmount_total(500.0); 
        invoice.setAmount_paid(0.0);

        when(invoicesRepository.findByIdWithLock(invoiceId)).thenReturn(Optional.of(invoice));

        // 2 & 3. Act & Assert
        assertThrows(RuntimeException.class, () -> {
            invoicesService.payInvoice(invoiceId, request);
        });
        
        verify(invoicesRepository, never()).save(any());
    }
}