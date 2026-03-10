package com.example.student.controller;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.dto.ClassesDTO;
import com.example.student.dto.InvoicesResponseDTO;
import com.example.student.dto.studentRequestDTO;
import com.example.student.dto.studentResponseDTO;
import com.example.student.exception.ApiResponse;
import com.example.student.service.InvoicesService;
import com.example.student.service.studentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class studentController {
    private final studentService service;
    private final InvoicesService invoicesService;
    @PostMapping
    public ResponseEntity<ApiResponse<studentResponseDTO>> createStudent(@Valid @RequestBody studentRequestDTO input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(service.createStudent(input), "Student created successfully"));
    }
    @GetMapping
    public ResponseEntity<Page<studentResponseDTO>> getAllStudents(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)Pageable pageable) {
    // Spring sẽ tự động lấy page, size, và sort từ URL để đổ vào đối tượng pageable
        return ResponseEntity.ok(service.getAllStudents(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<studentResponseDTO>> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(service.getStudentById(id), "Student retrieved successfully")); }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<studentResponseDTO>> updateStudent(@PathVariable Long id, @RequestBody studentRequestDTO requestDTO){
        return ResponseEntity.ok(ApiResponse.success(service.updateStudent(id, requestDTO), "Student updated successfully"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent (@PathVariable Long id){
        service.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Student deleted successfully"));
    }
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<String>> deactivateStudent(@PathVariable Long id) {
        String result = service.deactivateStudent(id);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<String>> activateStudent(@PathVariable Long id) {
        String result = service.activateStudent(id);
        return ResponseEntity.ok(ApiResponse.success(result, "Student activated successfully"));
    }
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<studentResponseDTO>> getStudentByEmail(@RequestParam String email) {
        return ResponseEntity.ok(ApiResponse.success(service.getStudentByEmail(email), "Student retrieved by email successfully"));
    }
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<studentResponseDTO>>> filterStudentsByGPA(@RequestParam Double minGpa, @RequestParam Double maxGpa) {
        return ResponseEntity.ok(ApiResponse.success(service.filterStudentsByGPA(minGpa, maxGpa), "Students filtered by GPA successfully"));
    }
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<studentResponseDTO>>> getActiveStudents() {
        return ResponseEntity.ok(ApiResponse.success(service.getActiveStudents(), "Active students retrieved successfully"));
    }
    @GetMapping("/{id}/classes")
    public ResponseEntity<ApiResponse<List<ClassesDTO>>> getClassesByStudent(@PathVariable("id") Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(service.getClassesByStudent(studentId),  "Lấy danh sách lớp học thành công"));
    }

    @GetMapping("/{id}/invoices")
    public ResponseEntity<ApiResponse<List<InvoicesResponseDTO>>> getStudentInvoices(@PathVariable("id") Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(invoicesService.getInvoicesByStudent(studentId), "Lấy danh sách hóa đơn thành công"));
    }
    
    
}
