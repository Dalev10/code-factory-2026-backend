package com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class UpdateBudgetAllocationRequest {
    
    @NotNull(message = "El ID del usuario es obligatorio para validar la propiedad")
    private UUID userId;

    @NotNull(message = "El nuevo monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a cero")
    private BigDecimal newAmount;
}