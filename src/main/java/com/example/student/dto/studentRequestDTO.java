package com.example.student.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class studentRequestDTO {
    private Long id;
    @NotBlank(message = "Tên không được để trống")
    private String name;
    @Email(message = "Email không hợp lệ")
    private String email;
    @DecimalMin(value = "0.0", message = "GPA phải lớn hơn hoặc bằng 0.0")
    @DecimalMax(value = "4.0", message = "GPA phải nhỏ hơn hoặc bằng 4.0")
    private Double gpa;
    @Min(value = 18, message = "Tuổi phải lớn hơn 18")
    private Integer age;
    private Boolean active = true;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

}
