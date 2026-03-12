package com.example.student.config;


import com.example.student.dto.StudentRequestDTO;
import com.example.student.dto.StudentResponseDTO;
import com.example.student.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentMapper {

    // Map từ Entity sang Response (Dùng cho các lệnh GET)
    StudentResponseDTO toResponse(StudentEntity entity);
    // Map từ Request sang Entity (Dùng cho POST)
    @Mapping(target = "updatedAt", ignore = true)
    StudentEntity toEntity(StudentRequestDTO request);
    @Mapping(target = "id", ignore = true)

    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(StudentRequestDTO dto, @MappingTarget StudentEntity entity);
}