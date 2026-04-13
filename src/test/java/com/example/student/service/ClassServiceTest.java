package com.example.student.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import com.example.student.config.ClassesMapper;
import com.example.student.config.StudentMapper;
import com.example.student.dto.ClassesDTO;
import com.example.student.dto.StudentResponseDTO;
import com.example.student.entity.ClassEntity;
import com.example.student.entity.StudentEntity;
import com.example.student.repository.ClassRepository;
import com.example.student.repository.EnrollmentRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClassServiceTest {

    @Mock private ClassRepository repository;
    @Mock private ClassesMapper classmapper;
    @Mock private EnrollmentRepository enrollmentRepository;
    @Mock private StudentMapper studentmapper;

    @InjectMocks private ClassService classService;

    @Test
    void testCreateClass_Success() {
        // 1. Arrange
        ClassesDTO request = new ClassesDTO(); 
        ClassEntity entity = new ClassEntity();
        ClassEntity savedEntity = new ClassEntity();
        
        when(classmapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(savedEntity);
        when(classmapper.toResponse(savedEntity)).thenReturn(request);

        // 2. Act
        ClassesDTO result = classService.createClass(request);

        // 3. Assert
        assertNotNull(result);
        verify(repository, times(1)).save(any());
    }

    @Test
    void testGetStudentsByClass_Success() {
        // 1. Arrange
        Long classId = 1L;
        StudentEntity s1 = new StudentEntity();
        StudentEntity s2 = new StudentEntity();
        List<StudentEntity> mockStudents = List.of(s1, s2);
        
        when(enrollmentRepository.findStudentsByClassId(classId)).thenReturn(mockStudents);
        // Giả lập mapper trả về DTO tương ứng
        when(studentmapper.toResponse(any(StudentEntity.class))).thenReturn(new StudentResponseDTO());

        // 2. Act
        List<StudentResponseDTO> result = classService.getStudentsByClass(classId);

        // 3. Assert
        assertEquals(2, result.size());
        verify(enrollmentRepository).findStudentsByClassId(classId);
    }
    @Test
    void testGetStudentsByClass_NoStudents() {
        // 1. Arrange
        Long classId = 1L;
        when(enrollmentRepository.findStudentsByClassId(classId)).thenReturn(List.of());

        // 2. Act
        List<StudentResponseDTO> result = classService.getStudentsByClass(classId);
        // 3. Assert
        assertTrue(result.isEmpty());
        verify(enrollmentRepository).findStudentsByClassId(classId);
    }
}