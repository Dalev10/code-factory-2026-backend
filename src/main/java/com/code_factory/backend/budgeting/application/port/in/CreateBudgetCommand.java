package com.code_factory.backend.budgeting.application.port.in;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateBudgetCommand(
        UUID userId,
        LocalDate month,
        BigDecimal totalIncome,
        BigDecimal expenseLimit
) {}
