package com.code_factory.backend.budgeting.application.port.in;

import com.code_factory.backend.budgeting.domain.model.BudgetProgressReport;

import java.time.LocalDate;
import java.util.UUID;

public interface GetBudgetProgressUseCase {
    BudgetProgressReport getProgress(UUID userId, LocalDate month);
}