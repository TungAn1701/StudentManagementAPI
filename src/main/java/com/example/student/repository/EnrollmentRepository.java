package com.example.student.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.student.entity.ClassEntity;
import com.example.student.entity.EnrollmentEntity;
import com.example.student.entity.studentEntity;
@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
    @Query("SELECT e.class_id FROM EnrollmentEntity e WHERE e.id.id = :studentId")
    List<ClassEntity> findClassesByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT e.id FROM EnrollmentEntity e WHERE e.class_id.class_id = :classId")
    List<studentEntity> findStudentsByClassId(@Param("classId") Long classId);

}
