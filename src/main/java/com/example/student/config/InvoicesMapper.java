package com.example.student.config;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import com.example.student.dto.InvoicesRequestDTO;
import com.example.student.dto.InvoicesResponseDTO;
import com.example.student.entity.InvoicesEntity;


@Mapper(componentModel = "spring")

public interface InvoicesMapper {

    @Mapping(source = "id.id", target = "student_id")
    @Mapping(source = "createdAt", target = "created_at")
    @Mapping(source = "updatedAt", target = "updated_at")
    
    InvoicesResponseDTO toResponse(InvoicesEntity entity);

   
    @Mapping(target = "invoice_id", ignore = true)
    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "amount_paid", constant = "0.0") 
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    InvoicesEntity toEntity(InvoicesRequestDTO dto);
}