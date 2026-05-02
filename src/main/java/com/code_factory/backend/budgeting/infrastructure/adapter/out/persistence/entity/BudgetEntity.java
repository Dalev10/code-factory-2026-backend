package com.code_factory.backend.budgeting.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "budgets",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "month"})
)
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class BudgetEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private LocalDate month;

    @Column(name = "total_income", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalIncome;

    @Column(name = "expense_limit", nullable = false, precision = 15, scale = 2)
    private BigDecimal expenseLimit;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
