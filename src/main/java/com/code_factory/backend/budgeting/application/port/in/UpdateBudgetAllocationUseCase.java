package com.code_factory.backend.budgeting.application.port.in;

import com.code_factory.backend.budgeting.domain.model.BudgetAllocation;

public interface UpdateBudgetAllocationUseCase {
    BudgetAllocation updateAllocation(UpdateBudgetAllocationCommand command);
}