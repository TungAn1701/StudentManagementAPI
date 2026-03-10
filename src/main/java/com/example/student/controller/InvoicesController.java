package com.example.student.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.example.student.dto.InvoicesRequestDTO;
import com.example.student.dto.InvoicesResponseDTO;
import com.example.student.dto.PaymentsRequestDTO;
import com.example.student.dto.PaymentsResponseDTO;
import com.example.student.exception.ApiResponse;
import com.example.student.service.InvoicesService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController

@RequiredArgsConstructor
public class InvoicesController {

    private final InvoicesService invoicesService;

    @PostMapping("/invoices")
    public ResponseEntity<ApiResponse<InvoicesResponseDTO>> createInvoice(@Valid @RequestBody InvoicesRequestDTO input) {
        InvoicesResponseDTO response = invoicesService.createInvoice(input);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Tạo hóa đơn thành công"));
    }
    @GetMapping("/students/{id}/invoices")
    public ResponseEntity<?> getStudentInvoices(@PathVariable("id") Long studentId) {
        List<InvoicesResponseDTO> result = invoicesService.getInvoicesByStudent(studentId);
        
        // Kiểm tra nếu sinh viên không có cái hóa đơn nào thì báo lỗi hoặc trả về list rỗng
        if (result.isEmpty()) {
            return ResponseEntity.ok("Sinh viên này hiện không có hóa đơn nào!");
        }
        
        return ResponseEntity.ok(result);
    }
    @PostMapping("/invoices/{id}/pay")
    public ResponseEntity<InvoicesResponseDTO> pay(@PathVariable("id") Long id, @RequestBody PaymentsRequestDTO request) {
  
    InvoicesResponseDTO result = invoicesService.payInvoice(id, request);
    return ResponseEntity.ok(result);
    }
    @GetMapping("/invoices/{id}/payments")
    public ResponseEntity<ApiResponse<List<PaymentsResponseDTO>>> getPayments(@PathVariable("id") Long id) {
    List<PaymentsResponseDTO> history = invoicesService.getPaymentHistory(id);
    
    return ResponseEntity.ok(ApiResponse.success(history, "Lấy lịch sử thanh toán thành công"));
}
}