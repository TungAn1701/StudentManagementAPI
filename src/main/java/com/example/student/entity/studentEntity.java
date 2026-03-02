package com.example.student.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class studentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "email", nullable = false, unique = true )
    private String email;
    @Column(name = "age")
    @Min(value = 18, message = "Tuổi phải lớn hơn 18")
    private Integer age;
    @Column(name = "gpa")
    @DecimalMin(value = "0.0", message = "GPA phải lớn hơn hoặc bằng 0.0")
    @DecimalMax(value = "4.0", message = "GPA phải nhỏ hơn hoặc bằng 4.0")
    private Double gpa;
    @Column(name = "active")
    private Boolean active = true;
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    @PreUpdate // Called before entity is updated
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}   
