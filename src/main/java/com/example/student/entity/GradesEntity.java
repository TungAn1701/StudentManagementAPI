package com.example.student.entity;
import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "grades")
public class GradesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grade_id;
    
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private EnrollmentEntity enrollment_id;

    @Column(name = "score")
    private Double score;

    @CreatedDate
    @Column(name = "graded_at")
    private LocalDateTime gradedAt;
        @PrePersist // Called before entity is persisted
    protected void onCreate() {
        gradedAt = LocalDateTime.now(); 
    }
}
