package com.example.student.repository;
import com.example.student.entity.studentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface studentRepository extends JpaRepository<studentEntity, Long> {

    Optional<studentEntity> findByEmail(String email);

    List<studentEntity> findByGpaBetween(Double minGpa, Double maxGpa);
    List<studentEntity> findByActiveTrue();
}
