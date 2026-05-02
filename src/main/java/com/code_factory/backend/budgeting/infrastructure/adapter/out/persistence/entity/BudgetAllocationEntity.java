// /src/main/java/com/code_factory/backend/budgeting/infrastructure/adapter/out/persistence/entity/BudgetAllocationEntity.java
package com.code_factory.backend.budgeting.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
    name = "budget_allocations",
    uniqueConstraints = @UniqueConstraint(columnNames = {"budget_id", "category_id"})
)
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class BudgetAllocationEntity {

    @Id
    private UUID id;

    @Column(name = "budget_id", nullable = false)
    private UUID budgetId;

    @Column(name = "category_id", nullable = false)
    private UUID categoryId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}