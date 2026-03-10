package com.example.student.config;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.student.dto.EnrollmentRequestDTO;
import com.example.student.dto.EnrollmentResponseDTO;
import com.example.student.entity.EnrollmentEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EnrollmentMapper {

    
    @Mapping(source = "id.id", target = "student_id") // Lấy ID từ object studentEntity
    @Mapping(source = "class_id.class_id", target = "class_id") // Lấy ID từ object ClassEntity
    @Mapping(source = "enrolledAt", target = "enrolled_at")
    EnrollmentResponseDTO toResponse(EnrollmentEntity entity);

    // Chuyển Request từ người dùng thành Entity để lưu (Phần này bạn làm đúng rồi)
    @Mapping(target = "enrollment_id", ignore = true)
    @Mapping(target = "id", ignore = true)       // Sẽ gán thủ công trong Service cho chắc chắn
    @Mapping(target = "class_id", ignore = true) // Sẽ gán thủ công trong Service cho chắc chắn
    @Mapping(target = "enrolledAt", ignore = true) // Thêm cái này để tắt luôn cảnh báo vàng
    EnrollmentEntity toEntity(EnrollmentRequestDTO dto);
}