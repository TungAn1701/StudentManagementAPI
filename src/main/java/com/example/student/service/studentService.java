package com.example.student.service;
import com.example.student.config.ClassesMapper;
import com.example.student.config.StudentMapper;
import com.example.student.dto.ClassesDTO;
import com.example.student.dto.StudentRequestDTO;
import com.example.student.dto.StudentResponseDTO;

import com.example.student.entity.ClassEntity;
import com.example.student.entity.StudentEntity;

import com.example.student.repository.EnrollmentRepository;
import com.example.student.repository.StudentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repository;
    private final StudentMapper studentMapper;
    private final ClassesMapper classesMapper;
    private final EnrollmentRepository enrollmentRepository;
    public StudentResponseDTO createStudent(StudentRequestDTO requestDTO){
        StudentEntity student = studentMapper.toEntity(requestDTO);
        StudentEntity savedStudent = repository.save(student);
        return studentMapper.toResponse(savedStudent);
    }
    public Page<StudentResponseDTO> getAllStudents(Pageable pageable) {
    // 1. Lấy dữ liệu từ DB bằng pageable có sẵn
    Page<StudentEntity> entities = repository.findAll(pageable);

    // 2. Map sang DTO
    return entities.map(entity -> studentMapper.toResponse(entity));
    }
    public StudentResponseDTO getStudentById(Long id) {
        StudentEntity student = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found "));
        return studentMapper.toResponse(student);}
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO requestDTO) {
        StudentEntity student = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found "));
        studentMapper.updateEntityFromDto(requestDTO, student);
        StudentEntity updatedStudent = repository.save(student);
        return studentMapper.toResponse(updatedStudent);
    }
    public void deleteStudent(Long id){
        StudentEntity student = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found "));
        repository.delete(student);
    }
    @Transactional
    public String deactivateStudent(Long id) {
        StudentEntity student = repository.findById(id)
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
        StudentEntity student = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found "));
        if(Boolean.TRUE.equals(student.getActive())) {
            throw new IllegalStateException("Student already active");
        }
        student.setActive(true) ;
        repository.save(student);
        return "Student activated successfully";
    }
    public StudentResponseDTO getStudentByEmail(String email) {
        StudentEntity student = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found with email: " + email));
        return studentMapper.toResponse(student);
    }
    public List<StudentResponseDTO> filterStudentsByGPA( Double minGpa, Double maxGpa) {
        List<StudentEntity> students = repository.findByGpaBetween(minGpa, maxGpa);
        return students.stream()
                .map(student -> studentMapper.toResponse(student))
                .toList();
    }
    public List<StudentResponseDTO> getActiveStudents(){
        List<StudentEntity> students = repository.findByActiveTrue();
        return students.stream()
                .map(student -> studentMapper.toResponse(student))
                .toList();
    }
    public List<ClassesDTO> getClassesByStudent(Long studentId) {
        // 1. Gọi Repo để lấy danh sách Entity
        List<ClassEntity> classes = enrollmentRepository.findClassesByStudentId(studentId);
        
        // 2. Dùng Stream để biến đổi List<Entity> thành List<DTO> trả về cho người dùng
        return classes.stream()
                .map(classesMapper::toResponse)
                .toList(); // Nếu dùng Java cũ hơn 16 thì dùng: .collect(Collectors.toList())
    }
    
}


