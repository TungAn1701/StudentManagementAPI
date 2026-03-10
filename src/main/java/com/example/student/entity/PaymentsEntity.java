package com.example.student.entity;


import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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

@Table(name = "payments")
public class PaymentsEntity extends CreateOnlyBaseEntity {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payment_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "invoice_id", nullable = false)
    private InvoicesEntity invoice_id;

    @Column(name = "amount")
    private Double amount;

    @Enumerated(jakarta.persistence.EnumType.STRING) 
    @Column(name = "method", nullable = false)
    private PaymentMethod method;
    @LastModifiedDate
    @Column(name = "paid_at")
    private LocalDateTime paidAt;
    @PrePersist // Called before entity is persisted
    protected void onCreate() {
        paidAt = LocalDateTime.now();
    }

    @PreUpdate // Called before entity is updated
    protected void onUpdate() {
        paidAt = LocalDateTime.now();
    }

}
