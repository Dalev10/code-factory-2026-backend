package com.code_factory.backend.budgeting.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class Budget {

    private final UUID id;
    private final UUID userId;
    private final LocalDate month;
    private final BigDecimal totalIncome;
    private final BigDecimal expenseLimit;
    private final LocalDateTime createdAt;

    /**
     * Actualiza el límite de gasto (presupuesto mensual)
     * Mantiene la inmutabilidad del objeto
     */
    public Budget updateExpenseLimit(BigDecimal newLimit) {

        if (newLimit == null || newLimit.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El valor debe ser mayor a 0");
        }

        return Budget.builder()
                .id(this.id)
                .userId(this.userId)
                .month(this.month)
                .totalIncome(this.totalIncome)
                .expenseLimit(newLimit)
                .createdAt(this.createdAt)
                .build();
    }
}
