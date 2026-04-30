package com.code_factory.backend.budgeting.application.service;

import com.code_factory.backend.budgeting.application.port.in.ListBudgetsUseCase;
import com.code_factory.backend.budgeting.application.port.out.BudgetRepositoryPort;
import com.code_factory.backend.budgeting.domain.model.Budget;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListBudgetsService implements ListBudgetsUseCase {

    private final BudgetRepositoryPort budgetRepositoryPort;

    @Override
    public List<Budget> getBudgetsByUserId(UUID userId) {
        return budgetRepositoryPort.findByUserId(userId);
    }
}
