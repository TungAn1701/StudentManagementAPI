package com.example.student.dto;

import java.time.LocalDateTime;

import com.example.student.entity.InvoicesStatus;

import lombok.Data;

@Data
public class InvoicesResponseDTO {
    private Long invoice_id;
    private Long student_id;
    private String term;
    private Double amount_total;
    private Double amount_paid;
    private LocalDateTime due_date;
    private InvoicesStatus status;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}