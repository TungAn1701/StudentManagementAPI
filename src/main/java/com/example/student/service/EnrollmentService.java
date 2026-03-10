package com.example.student.service;

import org.springframework.stereotype.Service;

import com.example.student.config.EnrollmentMapper;
import com.example.student.dto.EnrollmentRequestDTO;
import com.example.student.dto.EnrollmentResponseDTO; 
import com.example.student.entity.ClassEntity;
import com.example.student.entity.EnrollmentEntity;
import com.example.student.entity.studentEntity;
import com.example.student.repository.ClassRepository;
import com.example.student.repository.EnrollmentRepository;
import com.example.student.repository.studentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final studentRepository studentRepository;
    private final ClassRepository classRepository;
    private final EnrollmentMapper mapper;

    public EnrollmentResponseDTO enroll(EnrollmentRequestDTO dto) {
 
        studentEntity student = studentRepository.findById(dto.getStudent_id())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        

        ClassEntity clazz = classRepository.findById(dto.getClass_id())
                .orElseThrow(() -> new RuntimeException("Class not found"));

       
        EnrollmentEntity enrollment = new EnrollmentEntity();
        enrollment.setId(student);
        enrollment.setClass_id(clazz);

        
        EnrollmentEntity savedEnrollment = enrollmentRepository.save(enrollment);
        
        return mapper.toResponse(savedEnrollment);
    }
}