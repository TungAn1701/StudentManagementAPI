package com.example.student.service;
import com.example.student.config.StudentMapper;
import com.example.student.dto.studentRequestDTO;
import com.example.student.dto.studentResponseDTO;
import com.example.student.entity.studentEntity;
import com.example.student.repository.studentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class studentService {
    private final studentRepository repository;
    private final StudentMapper studentMapper;

    public studentResponseDTO createStudent(studentRequestDTO requestDTO){
        studentEntity student = studentMapper.toEntity(requestDTO);
        studentEntity savedStudent = repository.save(student);
        return studentMapper.toResponse(savedStudent);
    }
    public Page<studentResponseDTO> getAllStudents(Pageable pageable) {
    // 1. Lấy dữ liệu từ DB bằng pageable có sẵn
    Page<studentEntity> entities = repository.findAll(pageable);

    // 2. Map sang DTO
    return entities.map(entity -> studentMapper.toResponse(entity));
    }
    public studentResponseDTO getStudentById(Long id) {
        studentEntity student = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found "));
        return studentMapper.toResponse(student);}
    public studentResponseDTO updateStudent(Long id, studentRequestDTO requestDTO) {
        studentEntity student = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found "));
        studentMapper.updateEntityFromDto(requestDTO, student);
        studentEntity updatedStudent = repository.save(student);
        return studentMapper.toResponse(updatedStudent);
    }
    public void deleteStudent(Long id){
        studentEntity student = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found "));
        repository.delete(student);
    }
    @Transactional
    public String deactivateStudent(Long id) {
        studentEntity student = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found "));
        if(Boolean.FALSE.equals(student.getActive())) {
            throw new IllegalStateException("Student already inactive");
        }
        student.setActive(false) ;
        repository.save(student);
        return "Student deactivated successfully";
    }
    @Transactional
    public String activateStudent(Long id) {
        studentEntity student = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found "));
        if(Boolean.TRUE.equals(student.getActive())) {
            throw new IllegalStateException("Student already active");
        }
        student.setActive(true) ;
        repository.save(student);
        return "Student activated successfully";
    }
    public studentResponseDTO getStudentByEmail(String email) {
        studentEntity student = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found with email: " + email));
        return studentMapper.toResponse(student);
    }
    public List<studentResponseDTO> filterStudentsByGPA( Double minGpa, Double maxGpa) {
        List<studentEntity> students = repository.findByGpaBetween(minGpa, maxGpa);
        return students.stream()
                .map(student -> studentMapper.toResponse(student))
                .toList();
    }
    public List<studentResponseDTO> getActiveStudents(){
        List<studentEntity> students = repository.findByActiveTrue();
        return students.stream()
                .map(student -> studentMapper.toResponse(student))
                .toList();
    }

}


