package com.example.student.service;
import com.example.student.config.ClassesMapper;
import com.example.student.config.StudentMapper;
import com.example.student.dto.ClassesDTO;
import com.example.student.dto.studentResponseDTO;
import com.example.student.entity.ClassEntity;
import com.example.student.entity.studentEntity;
import com.example.student.repository.ClassRepository;
import com.example.student.repository.EnrollmentRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class ClassService {
    private final ClassRepository repository;
    private final ClassesMapper classmapper;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentMapper studentmapper;
    public ClassesDTO createClass(ClassesDTO requestDTO){
        ClassEntity classEntity = classmapper.toEntity(requestDTO);
        ClassEntity savedClass = repository.save(classEntity);
        return classmapper.toResponse(savedClass);
    }
    public List<studentResponseDTO> getStudentsByClass(Long classId) {
        
        List<studentEntity> students = enrollmentRepository.findStudentsByClassId(classId);
        
        
        return students.stream().map(studentmapper::toResponse).toList();
    }
}
