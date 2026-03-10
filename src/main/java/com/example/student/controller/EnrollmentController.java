package com.example.student.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; // Đảm bảo dùng đúng import này
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.dto.EnrollmentRequestDTO;
import com.example.student.dto.EnrollmentResponseDTO;
import com.example.student.exception.ApiResponse;
import com.example.student.service.EnrollmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService service;

    @PostMapping
    public ResponseEntity<ApiResponse<EnrollmentResponseDTO>> enrollStudent(@Valid @RequestBody EnrollmentRequestDTO input) {
        EnrollmentResponseDTO response = service.enroll(input);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Student enrolled successfully"));
    }
}