package com.example.student.dto;


import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EnrollmentResponseDTO {
    private Long enrollment_id;
    private Long student_id;
    private Long class_id;
    private LocalDateTime enrolled_at;
}