package com.example.student.config;


import com.example.student.dto.studentRequestDTO;
import com.example.student.dto.studentResponseDTO;
import com.example.student.entity.studentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentMapper {

    // Map từ Entity sang Response (Dùng cho các lệnh GET)
    studentResponseDTO toResponse(studentEntity entity);
    // Map từ Request sang Entity (Dùng cho POST)
    @Mapping(target = "updatedAt", ignore = true)
    studentEntity toEntity(studentRequestDTO request);
    @Mapping(target = "id", ignore = true)

    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(studentRequestDTO dto, @MappingTarget studentEntity entity);
}