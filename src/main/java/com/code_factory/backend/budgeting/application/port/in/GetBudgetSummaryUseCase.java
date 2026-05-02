package com.code_factory.backend.budgeting.application.port.in;

import com.code_factory.backend.budgeting.domain.model.BudgetSummary;

import java.time.LocalDate;
import java.util.UUID;

public interface GetBudgetSummaryUseCase {
    BudgetSummary getSummary(UUID userId, LocalDate month);
}