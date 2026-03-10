package com.example.student.entity;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass 
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity extends CreateOnlyBaseEntity{

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @PrePersist // Called before entity is persisted
    protected void onCreate() {
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate // Called before entity is updated
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
