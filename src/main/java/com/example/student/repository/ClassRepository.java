package com.example.student.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student.entity.ClassEntity;
@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Long> {

}