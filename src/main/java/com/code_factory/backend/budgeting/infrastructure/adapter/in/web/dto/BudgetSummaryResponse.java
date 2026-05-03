package com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class BudgetSummaryResponse {
    private UUID budgetId;
    private BigDecimal totalIncome;
    private BigDecimal expenseLimit;
    private BigDecimal totalSpent;
    private BigDecimal remainingBalance;
    private boolean isExceeded;
}