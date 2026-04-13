    package com.example.student.service;

    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.ArgumentMatchers.*;
    import static org.mockito.Mockito.*;

    import java.util.List;
    import java.util.Optional;

    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.junit.jupiter.MockitoExtension;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageImpl;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;

    import com.example.student.config.ClassesMapper;
    import com.example.student.config.StudentMapper;
    import com.example.student.dto.ClassesDTO;
    import com.example.student.dto.StudentRequestDTO;
    import com.example.student.dto.StudentResponseDTO;
    import com.example.student.entity.ClassEntity;
    import com.example.student.entity.StudentEntity;
    import com.example.student.repository.EnrollmentRepository;
    import com.example.student.repository.StudentRepository;

    @ExtendWith(MockitoExtension.class) // Dùng cái này thay vì mở xưởng thủ công
    class StudentServiceTest {

        @Mock private StudentRepository repository;
        @Mock private StudentMapper studentMapper;
        @Mock private ClassesMapper classesMapper;
        @Mock private EnrollmentRepository enrollmentRepository;

        @InjectMocks private StudentService studentService;

        private StudentEntity student;
        private StudentResponseDTO responseDTO;

        @BeforeEach
        void setUp() {
            student = new StudentEntity();
            student.setId(1L);
            student.setEmail("tung.an@student.com");
            student.setActive(true);

            responseDTO = new StudentResponseDTO();
            responseDTO.setId(1L);
            responseDTO.setEmail("tung.an@student.com");
            responseDTO.setActive(true);
        }



        @Test
        void testCreateStudent() {
            StudentRequestDTO request = new StudentRequestDTO();
            when(studentMapper.toEntity(any())).thenReturn(student);
            when(repository.save(any())).thenReturn(student);
            when(studentMapper.toResponse(any())).thenReturn(responseDTO);

            StudentResponseDTO result = studentService.createStudent(request);

            assertNotNull(result);
            assertEquals(student.getEmail(), result.getEmail());
            verify(repository, times(1)).save(any());
        }

        @Test
        void testUpdateStudent() {
            StudentRequestDTO request = new StudentRequestDTO();
            when(repository.findById(1L)).thenReturn(Optional.of(student));
            when(repository.save(any())).thenReturn(student);
            when(studentMapper.toResponse(any())).thenReturn(responseDTO);

            StudentResponseDTO result = studentService.updateStudent(1L, request);

            assertNotNull(result);
            verify(studentMapper).updateEntityFromDto(eq(request), any());
        }


        @Test
        void testGetAllStudents() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<StudentEntity> page = new PageImpl<>(List.of(student));
            
            when(repository.findAll(pageable)).thenReturn(page);
            when(studentMapper.toResponse(any())).thenReturn(responseDTO);

            Page<StudentResponseDTO> result = studentService.getAllStudents(pageable);

            assertEquals(1, result.getContent().size());
            assertEquals(responseDTO.getEmail(), result.getContent().get(0).getEmail());
        }

        @Test
        void testGetStudentById() {
            when(repository.findById(99L)).thenReturn(Optional.empty());

        
            Exception exception = assertThrows(RuntimeException.class, () -> {
                studentService.getStudentById(99L);
            });

            assertTrue(exception.getMessage().contains("Student not found"));
        }



        @Test
        void testDeactivateStudent() {
            when(repository.findById(1L)).thenReturn(Optional.of(student));

            String message = studentService.deactivateStudent(1L);

            assertFalse(student.getActive()); 
            assertEquals("Student deactivated successfully", message);
            verify(repository).save(student);
        }

        @Test
        void testDeactivateStudent_AlreadyInactive() {
            student.setActive(false);
            when(repository.findById(1L)).thenReturn(Optional.of(student));

    
            assertThrows(IllegalStateException.class, () -> {
                studentService.deactivateStudent(1L);
            });
        }


        @Test
        void testGetClassesByStudent() {
            ClassEntity classEntity = new ClassEntity();
            classEntity.setClass_id(101L);
            ClassesDTO classDTO = new ClassesDTO();
            
            when(enrollmentRepository.findClassesByStudentId(1L)).thenReturn(List.of(classEntity));
            when(classesMapper.toResponse(any())).thenReturn(classDTO);

            List<ClassesDTO> result = studentService.getClassesByStudent(1L);

            assertFalse(result.isEmpty());
            assertEquals(1, result.size());
            verify(classesMapper).toResponse(classEntity);
        }


        @Test
        void testFilterStudentsByGPA() {
            when(repository.findByGpaBetween(3.0, 4.0)).thenReturn(List.of(student));
            when(studentMapper.toResponse(any())).thenReturn(responseDTO);

            List<StudentResponseDTO> result = studentService.filterStudentsByGPA(3.0, 4.0);

            assertEquals(1, result.size());
            verify(repository).findByGpaBetween(3.0, 4.0);
        }
        @Test
        void testGetStudentByEmail() {
            // Arrange
            String email = "tung.an@student.com";
            when(repository.findByEmail(email)).thenReturn(Optional.of(student));
            when(studentMapper.toResponse(student)).thenReturn(responseDTO);

            // Act
            StudentResponseDTO result = studentService.getStudentByEmail(email);

            // Assert
            assertNotNull(result);
            assertEquals(email, result.getEmail());
        }

        @Test
        void testGetStudentById_Success() {
            // Arrange
            Long id = 1L;
            when(repository.findById(id)).thenReturn(Optional.of(student));
            when(studentMapper.toResponse(student)).thenReturn(responseDTO);

            // Act
            StudentResponseDTO result = studentService.getStudentById(id);

            // Assert
            assertNotNull(result);
            assertEquals(id, result.getId());
        }

        @Test
        void testActivateStudent_Success() {
            // Arrange
            student.setActive(false); 
            when(repository.findById(1L)).thenReturn(Optional.of(student));

            // Act
            String message = studentService.activateStudent(1L);

            // Assert
            assertTrue(student.getActive());
            assertEquals("Student activated successfully", message);
            verify(repository).save(student);
        }
        @Test
    void testDeleteStudent_Success() {
        // 1. Arrange: Giả lập tìm thấy sinh viên có ID = 1
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(student));
        
        // Vì repository.delete(student) trả về void, nên ta không cần 'when' 
        // Mockito sẽ tự hiểu là không làm gì cả (doNothing)

        // 2. Act
        studentService.deleteStudent(id);

        // 3. Assert: Kiểm tra xem repository có thực sự gọi hàm delete với đúng object student không
        verify(repository, times(1)).delete(student);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testDeleteStudent_NotFound() {
        // Test kịch bản xóa một đứa không tồn tại (để check xem có ném lỗi ko)
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            studentService.deleteStudent(id);
        });

        // Đảm bảo lệnh delete KHÔNG bao giờ được gọi nếu không tìm thấy ID
        verify(repository, never()).delete(any());
    }
    }