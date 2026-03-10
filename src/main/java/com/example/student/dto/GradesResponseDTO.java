package com.example.student.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class GradesResponseDTO {
    private Long grade_id;
    private Long enrollment_id;
    private Double score;
    private LocalDateTime graded_at;
}