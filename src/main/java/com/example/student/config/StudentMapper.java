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
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    // Map từ Request sang Entity (Dùng cho POST)
    studentEntity toEntity(studentRequestDTO request);
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    // Cập nhật Entity từ DTO (Dùng cho PUT/PATCH)
    // Nó sẽ tự động bỏ qua các trường null nếu bạn cấu hình IGNORE ở trên
    void updateEntityFromDto(studentRequestDTO dto, @MappingTarget studentEntity entity);
}   