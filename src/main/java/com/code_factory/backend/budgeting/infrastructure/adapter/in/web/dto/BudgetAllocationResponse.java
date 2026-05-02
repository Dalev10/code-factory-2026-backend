package com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class BudgetAllocationResponse {
    private UUID id;
    private UUID budgetId;
    private UUID categoryId;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}