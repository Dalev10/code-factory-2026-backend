package com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateBudgetRequest {

    @NotNull(message = "El ID de usuario es obligatorio")
    private UUID userId;

    @NotNull(message = "El mes es obligatorio")
    private LocalDate month;

    @NotNull(message = "Los ingresos son obligatorios")
    @Positive(message = "Los ingresos deben ser mayores a 0")
    private BigDecimal totalIncome;

    @NotNull(message = "El límite de gastos es obligatorio")
    @Positive(message = "El límite de gastos debe ser mayor a 0")
    private BigDecimal expenseLimit;
}
