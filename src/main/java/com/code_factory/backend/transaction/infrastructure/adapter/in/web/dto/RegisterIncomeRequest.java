package com.code_factory.backend.transaction.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class RegisterIncomeRequest {
    @NotNull @Positive private BigDecimal amount;
    private String description;
    @NotNull private LocalDate transactionDate;
    @NotNull private UUID userId;
    @NotNull private UUID categoryId;
}