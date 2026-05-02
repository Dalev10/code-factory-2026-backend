package com.code_factory.backend.budgeting.application.service;

import com.code_factory.backend.budgeting.application.port.in.DeleteBudgetUseCase;
import com.code_factory.backend.budgeting.application.port.out.BudgetRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteBudgetService implements DeleteBudgetUseCase {

    private final BudgetRepositoryPort budgetRepositoryPort;

    @Override
    public boolean deleteById(UUID budgetId) {

        if (budgetRepositoryPort.findById(budgetId).isEmpty()) {
            return false;
        }

        budgetRepositoryPort.deleteById(budgetId);
        return true;
    }
}