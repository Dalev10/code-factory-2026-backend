package com.code_factory.backend.budgeting.application.port.in;

import java.util.UUID;

public interface DeleteBudgetUseCase {
    boolean deleteById(UUID budgetId);
}