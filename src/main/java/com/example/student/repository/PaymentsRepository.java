package com.example.student.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.student.entity.PaymentsEntity;

@Repository
public interface PaymentsRepository extends JpaRepository<PaymentsEntity, Long> {
    
    @Query("SELECT p FROM PaymentsEntity p WHERE p.invoice_id.invoice_id = :invoiceId")
    List<PaymentsEntity> findByInvoiceId(@Param("invoiceId") Long invoiceId);
    
}