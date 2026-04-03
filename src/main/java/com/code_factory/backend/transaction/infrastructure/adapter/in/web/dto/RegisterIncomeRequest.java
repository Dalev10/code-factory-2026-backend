package com.code_factory.backend.transaction.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class RegisterIncomeRequest {
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    private BigDecimal amount;

    private String description;
    
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate transactionDate;
    
    @NotNull(message = "El ID de usuario es obligatorio")
    private UUID userId;
    
    @NotNull(message = "El ID de categoría es obligatorio")
    private UUID categoryId;
}