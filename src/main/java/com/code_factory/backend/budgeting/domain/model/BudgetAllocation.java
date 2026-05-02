package com.code_factory.backend.budgeting.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad de Dominio: BudgetAllocation
 * Representa la asignación específica de un límite de gasto a una categoría dentro de un presupuesto mensual.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetAllocation {
    
    private UUID id;
    private UUID budgetId;
    private UUID categoryId;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    
}