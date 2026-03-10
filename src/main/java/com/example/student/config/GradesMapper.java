package com.example.student.config;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.student.dto.GradesRequestDTO;
import com.example.student.dto.GradesResponseDTO;
import com.example.student.entity.GradesEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GradesMapper {

    // Lấy ID từ trong object EnrollmentEntity
    @Mapping(source = "enrollment_id.enrollment_id", target = "enrollment_id") 
    @Mapping(source = "gradedAt", target = "graded_at")
    GradesResponseDTO toResponse(GradesEntity entity);

    @Mapping(target = "grade_id", ignore = true)
    @Mapping(target = "enrollment_id", ignore = true) // Sẽ gán bằng tay trong Service
    @Mapping(target = "gradedAt", ignore = true)
    GradesEntity toEntity(GradesRequestDTO dto);
}