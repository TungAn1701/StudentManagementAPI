package com.example.student.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GradesRequestDTO {
    @NotNull(message = "Enrollment ID is required")
    private Long enrollment_id;

    @NotNull(message = "Score is required")
    @Min(value = 0, message = "Điểm không được nhỏ hơn 0")
    @Max(value = 10, message = "Điểm không được lớn hơn 10")
    private Double score;
}