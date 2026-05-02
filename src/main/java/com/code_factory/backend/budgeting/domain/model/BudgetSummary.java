package com.code_factory.backend.budgeting.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record BudgetSummary(
        UUID budgetId,
        BigDecimal totalIncome,
        BigDecimal expenseLimit,
        BigDecimal totalSpent,
        BigDecimal remainingBalance,
        boolean isExceeded
) {}