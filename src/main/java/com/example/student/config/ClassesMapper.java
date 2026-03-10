package com.example.student.config;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.student.dto.ClassesDTO;
import com.example.student.entity.ClassEntity;
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClassesMapper {
    ClassesDTO toResponse(ClassEntity entity) ;
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ClassEntity toEntity(ClassesDTO request);
    @Mapping(target = "class_id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(ClassesDTO dto, @MappingTarget ClassEntity entity);
    
}
