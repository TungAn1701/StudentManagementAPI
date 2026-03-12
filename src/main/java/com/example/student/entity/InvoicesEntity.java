package com.example.student.entity;
import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import jakarta.persistence.FetchType;
import jakarta.persistence.CheckConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "invoices", 
    check = @CheckConstraint(name = "check_invoice_amount", constraint = "amount_paid <= amount_total")
)
public class InvoicesEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoice_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id", nullable = false)
    private StudentEntity id;

    @Column(name = "term")
    private String term;

    @Column(name = "amount_total")
    private Double amount_total;

    @Enumerated(EnumType.STRING) 
    @Column(name = "status", nullable = false)
    private InvoicesStatus status = InvoicesStatus.UNPAID;

    @Column(name = "amount_paid")
    private Double amount_paid = 0.0;

    @Future
    @Column(name = "due_date")
    private LocalDateTime due_date;
    
}
