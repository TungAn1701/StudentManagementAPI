package com.example.student.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.student.config.EnrollmentMapper;
import com.example.student.dto.EnrollmentRequestDTO;
import com.example.student.dto.EnrollmentResponseDTO;
import com.example.student.entity.ClassEntity;
import com.example.student.entity.EnrollmentEntity;
import com.example.student.entity.StudentEntity;
import com.example.student.repository.ClassRepository;
import com.example.student.repository.EnrollmentRepository;
import com.example.student.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTest {
    @Mock private EnrollmentRepository enrollmentRepository;
    @Mock private StudentRepository studentRepository;
    @Mock private ClassRepository classRepository;
    @Mock private EnrollmentMapper mapper;
    @InjectMocks private EnrollmentService enrollmentService;
    @Test
    void testEnroll_Success() {
        // 1. Arrange
        EnrollmentRequestDTO request = new EnrollmentRequestDTO();
        request.setStudent_id(1L);
        request.setClass_id(1L);

        StudentEntity student = new StudentEntity();
        ClassEntity clazz = new ClassEntity();
        EnrollmentEntity enrollment = new EnrollmentEntity();
        EnrollmentResponseDTO response = new EnrollmentResponseDTO();

        when(studentRepository.findById(request.getStudent_id())).thenReturn(Optional.of(student));
        when(classRepository.findById(request.getClass_id())).thenReturn(Optional.of(clazz));
        when(enrollmentRepository.save(any())).thenReturn(enrollment);
        when(mapper.toResponse(enrollment)).thenReturn(response);

        // 2. Act
        EnrollmentResponseDTO result = enrollmentService.enroll(request);
        
        // 3. Assert
        assertNotNull(result);
        verify(enrollmentRepository, times(1)).save(any());

    }
}
