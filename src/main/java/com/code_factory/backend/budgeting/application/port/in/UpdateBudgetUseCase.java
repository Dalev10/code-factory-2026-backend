package com.code_factory.backend.budgeting.application.port.in;

import com.code_factory.backend.budgeting.domain.model.Budget;

public interface UpdateBudgetUseCase {
    Budget updateBudget(UpdateBudgetCommand command);
}

