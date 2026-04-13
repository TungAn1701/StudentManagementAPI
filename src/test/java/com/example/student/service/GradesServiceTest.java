package com.example.student.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.example.student.config.GradesMapper;
import com.example.student.dto.GradesRequestDTO;
import com.example.student.dto.GradesResponseDTO;
import com.example.student.entity.EnrollmentEntity;
import com.example.student.entity.GradesEntity;
import com.example.student.repository.EnrollmentRepository;
import com.example.student.repository.GradesRepository;
@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class GradesServiceTest {
    @Mock private GradesRepository gradesRepository;
    @Mock private EnrollmentRepository enrollmentRepository;
    @Mock private GradesMapper mapper;  
    @InjectMocks private GradesService gradesService;
    @Test
    void testCreateOrUpdateGrade() {
            // 1. Arrange
            GradesRequestDTO request = new GradesRequestDTO();
            request.setEnrollment_id(1L);
            request.setScore(85.0);
    
            EnrollmentEntity enrollment = new EnrollmentEntity();
            GradesEntity grade = new GradesEntity();
            GradesResponseDTO response = new GradesResponseDTO();
    
            when(enrollmentRepository.findById(request.getEnrollment_id())).thenReturn(java.util.Optional.of(enrollment));
            when(gradesRepository.findByEnrollmentId(request.getEnrollment_id())).thenReturn(java.util.Optional.empty());
            when(gradesRepository.save(any())).thenReturn(grade);
            when(mapper.toResponse(grade)).thenReturn(response);
    
            // 2. Act
            GradesResponseDTO result = gradesService.createOrUpdateGrade(request);
    
            // 3. Assert
            assertNotNull(result);
            verify(gradesRepository, times(1)).save(any()); 
    }
}
