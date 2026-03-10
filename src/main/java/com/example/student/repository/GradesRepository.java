package com.example.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.student.entity.GradesEntity;

public interface GradesRepository extends JpaRepository<GradesEntity, Long> {
    @Query("SELECT g FROM GradesEntity g WHERE g.enrollment_id.enrollment_id = :enrollmentId")
    Optional<GradesEntity> findByEnrollmentId(@Param("enrollmentId") Long enrollmentId);
}