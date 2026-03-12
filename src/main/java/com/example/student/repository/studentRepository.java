package com.example.student.repository;
import com.example.student.entity.StudentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    Optional<StudentEntity> findByEmail(String email);

    List<StudentEntity> findByGpaBetween(Double minGpa, Double maxGpa);
    List<StudentEntity> findByActiveTrue();
}
