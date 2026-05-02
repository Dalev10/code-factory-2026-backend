package com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto;

import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class BudgetResponse {
    private UUID id;
    private UUID userId;
    private LocalDate month;
    private BigDecimal totalIncome;
    private BigDecimal expenseLimit;
    private LocalDateTime createdAt;
}
