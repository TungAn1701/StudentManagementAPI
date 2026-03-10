package com.example.student.service;

import org.springframework.stereotype.Service;

import com.example.student.config.GradesMapper;
import com.example.student.dto.GradesRequestDTO;
import com.example.student.dto.GradesResponseDTO;
import com.example.student.entity.EnrollmentEntity;
import com.example.student.entity.GradesEntity;
import com.example.student.repository.EnrollmentRepository;
import com.example.student.repository.GradesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GradesService {
    private final GradesRepository gradesRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final GradesMapper mapper;

    public GradesResponseDTO createOrUpdateGrade(GradesRequestDTO dto) {
    
        EnrollmentEntity enrollment = enrollmentRepository.findById(dto.getEnrollment_id())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin đăng ký lớp học này"));

        GradesEntity grade = gradesRepository.findByEnrollmentId(dto.getEnrollment_id())
                .orElse(new GradesEntity()); 

        grade.setEnrollment_id(enrollment); 
        grade.setScore(dto.getScore());     

        
        GradesEntity savedGrade = gradesRepository.save(grade);
        return mapper.toResponse(savedGrade);
    }
}