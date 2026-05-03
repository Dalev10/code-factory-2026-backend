package com.code_factory.backend.budgeting.application.port.in;

import java.util.UUID;
import java.math.BigDecimal;

public record UpdateBudgetCommand(
        UUID userId,
        UUID budgetId,
        BigDecimal newTotalIncome,
        BigDecimal newLimit
) {}
