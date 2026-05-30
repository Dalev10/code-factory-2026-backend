package com.code_factory.backend.alerts.infrastructure.adapter.in.web.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BudgetAlertResponse {
    private boolean hasBudget;
    private String alertLevel;
    private double consumedPercentage;
    private BigDecimal expenseLimit;
    private BigDecimal totalSpent;
    private BigDecimal remainingBalance;
    private double thresholdPercentage;
    private String message;
}
