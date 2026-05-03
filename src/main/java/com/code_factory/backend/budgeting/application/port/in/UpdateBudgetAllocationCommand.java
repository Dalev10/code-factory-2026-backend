package com.code_factory.backend.budgeting.application.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateBudgetAllocationCommand(
        UUID userId,
        UUID budgetId,
        UUID categoryId,
        BigDecimal newAmount
) {}