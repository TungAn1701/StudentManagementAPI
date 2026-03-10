package com.example.student.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.student.entity.InvoicesEntity;

import jakarta.persistence.LockModeType;

public interface InvoicesRespository extends JpaRepository<InvoicesEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM InvoicesEntity i WHERE i.invoice_id = :id")
    Optional<InvoicesEntity> findByIdWithLock(@Param("id") Long id);
    
    @Query("SELECT i FROM InvoicesEntity i WHERE i.id.id = :studentId")
    List<InvoicesEntity> findByStudentId(@Param("studentId") Long studentId);
}