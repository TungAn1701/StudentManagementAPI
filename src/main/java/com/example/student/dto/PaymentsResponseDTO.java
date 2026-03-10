package com.example.student.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PaymentsResponseDTO {
    private Long payment_id;
    private Long invoice_id; // Chúng ta chỉ cần ID hóa đơn ở đây
    private Double amount;
    private String method;
    private LocalDateTime created_at; // Lấy từ BaseEntity để biết ngày nộp
    private LocalDateTime paid_at;
}