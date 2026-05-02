package com.code_factory.backend.budgeting.application.port.in;

import com.code_factory.backend.budgeting.domain.model.Budget;
import java.util.List;
import java.util.UUID;

public interface ListBudgetsUseCase {
    List<Budget> getBudgetsByUserId(UUID userId);
}
