package com.example.student.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InvoicesRequestDTO {
    @NotNull(message = "Student ID is required")
    private Long student_id;

    @NotBlank(message = "Kỳ học không được để trống")
    private String term;

    @NotNull(message = "Tổng tiền không được để trống")
    @Min(value = 0, message = "Số tiền không được âm")
    private Double amount_total;

    @Future(message = "Hạn chót thanh toán phải ở tương lai")
    private LocalDateTime due_date;
}