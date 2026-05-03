package com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UpdateBudgetRequest {

    @NotNull(message = "El nuevo ingreso total es obligatorio")
    @Positive(message = "El ingreso total debe ser mayor a cero")
    private BigDecimal newTotalIncome;

    @NotNull(message = "El nuevo límite de gastos es obligatorio")
    @Positive(message = "El valor debe ser mayor a cero")
    private BigDecimal newLimit;
}