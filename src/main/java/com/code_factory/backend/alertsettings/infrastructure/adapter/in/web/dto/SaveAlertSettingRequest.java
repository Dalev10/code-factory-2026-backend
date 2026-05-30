package com.code_factory.backend.alertsettings.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaveAlertSettingRequest {

    @NotNull(message = "El límite de gasto es obligatorio.")
    @Positive(message = "El límite de gasto debe ser mayor a cero.")
    private BigDecimal expenseLimit;
}
