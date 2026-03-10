package com.example.student.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnrollmentRequestDTO {
    @NotNull(message = "Student ID is required")
    private Long student_id;

    @NotNull(message = "Class ID is required")
    private Long class_id;
    
}