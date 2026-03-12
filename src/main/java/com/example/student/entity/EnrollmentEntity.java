package com.example.student.entity;
import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "enrollments", 
    uniqueConstraints = {
        
        @UniqueConstraint(name = "uc_student_class", columnNames = {"student_id", "class_id"})
    },
    indexes = {
        
        @Index(name = "idx_enrollment_student", columnList = "student_id"),
        @Index(name = "idx_enrollment_class", columnList = "class_id")
    }
)
public class EnrollmentEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollment_id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity class_id;

    @CreatedDate
    @Column(name = "enrolled_at")
    private LocalDateTime enrolledAt;
        @PrePersist // Called before entity is persisted
    protected void onCreate() {
        enrolledAt = LocalDateTime.now();
    }
}
