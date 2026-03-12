package com.example.student.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.dto.ClassesDTO;
import com.example.student.dto.StudentResponseDTO;
import com.example.student.exception.ApiResponse;
import com.example.student.service.ClassService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController

@RequiredArgsConstructor
public class ClassController {
    private final ClassService service;
    @PostMapping("/classes")
    public ResponseEntity<ApiResponse<ClassesDTO>> createClass(@Valid @RequestBody ClassesDTO input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(service.createClass(input), "Class created successfully"));
    }
    @GetMapping("/classes/{id}/students")
    public ResponseEntity<ApiResponse<List<StudentResponseDTO>>> getStudentsByClass(@PathVariable("id") Long classId) {
        return ResponseEntity.ok(ApiResponse.success(
            service.getStudentsByClass(classId), 
            "Lấy danh sách sinh viên thành công"
        ));
    }
}
