package com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class UpdateBudgetRequest {

    @NotNull
    private UUID userId;

    @NotNull
    @Positive(message = "El valor debe ser mayor a 0")
    private BigDecimal newLimit;
}
