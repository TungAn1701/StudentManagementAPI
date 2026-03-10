package com.example.student.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.dto.GradesRequestDTO;
import com.example.student.dto.GradesResponseDTO;
import com.example.student.exception.ApiResponse;
import com.example.student.service.GradesService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradesController {

    private final GradesService gradesService;

    @PostMapping
    public ResponseEntity<ApiResponse<GradesResponseDTO>> createOrUpdateGrade(@Valid @RequestBody GradesRequestDTO input) {
        GradesResponseDTO response = gradesService.createOrUpdateGrade(input);
        
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response, "Cập nhật điểm số thành công"));
    }
}