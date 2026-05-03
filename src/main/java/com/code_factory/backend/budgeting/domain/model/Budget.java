package com.code_factory.backend.budgeting.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class Budget {

    private final UUID id;
    private final UUID userId;
    private final LocalDate month;
    private final BigDecimal totalIncome;
    private final BigDecimal expenseLimit;
    private final LocalDateTime createdAt;

}