package com.code_factory.backend.transaction.application.port.in;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RegisterIncomeCommand(
    UUID userId,
    UUID categoryId,
    BigDecimal amount,
    String description,
    LocalDate transactionDate
) {}