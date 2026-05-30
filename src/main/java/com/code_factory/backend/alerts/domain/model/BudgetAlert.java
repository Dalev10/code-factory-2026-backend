package com.code_factory.backend.alerts.domain.model;

import java.math.BigDecimal;

public record BudgetAlert(
        boolean hasBudget,
        AlertLevel alertLevel,
        double consumedPercentage,
        BigDecimal expenseLimit,
        BigDecimal totalSpent,
        BigDecimal remainingBalance,
        double thresholdPercentage,
        String message
) {}
