package com.example.student.config;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.student.entity.PaymentsEntity;
import com.example.student.dto.PaymentsResponseDTO;

@Mapper(componentModel = "spring")
public interface PaymentsMapper {

   
   @Mapping(source = "paidAt", target = "paid_at")

    @Mapping(source = "createdAt", target = "created_at")
    
    @Mapping(source = "invoice_id.invoice_id", target = "invoice_id")
    PaymentsResponseDTO toResponse(PaymentsEntity entity);
}