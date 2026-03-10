package com.example.student.dto;

import com.example.student.entity.PaymentMethod;

import jakarta.validation.constraints.Min;
import lombok.Data;
@Data
public class PaymentsRequestDTO {
    @Min(value = 1, message = "Số tiền nộp phải lớn hơn 0")
    private Double amount;
    private PaymentMethod method;
}
