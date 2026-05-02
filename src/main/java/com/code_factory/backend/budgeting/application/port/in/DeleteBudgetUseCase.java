package com.code_factory.backend.budgeting.application.port.in;

public interface DeleteBudgetUseCase {
    void deleteBudget(DeleteBudgetCommand command);
}