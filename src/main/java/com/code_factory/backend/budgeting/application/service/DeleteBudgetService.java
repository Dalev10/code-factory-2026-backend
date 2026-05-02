package com.code_factory.backend.budgeting.application.service;

import com.code_factory.backend.budgeting.application.port.in.DeleteBudgetUseCase;
import com.code_factory.backend.budgeting.application.port.out.BudgetRepositoryPort;
import com.code_factory.backend.budgeting.domain.model.Budget;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteBudgetService implements DeleteBudgetUseCase {

    private final BudgetRepositoryPort budgetRepositoryPort;

    @Override
    public boolean deleteById(UUID budgetId) {

        var optionalBudget = budgetRepositoryPort.findById(budgetId);

        if (optionalBudget.isEmpty()) {
            return false;
        }

        var budget = optionalBudget.get();

        // 🔥 crear nuevo presupuesto en 0 (inmutable)
        Budget updatedBudget = Budget.builder()
                .id(budget.getId())
                .userId(budget.getUserId())
                .month(budget.getMonth())
                .totalIncome(BigDecimal.ZERO)
                .expenseLimit(BigDecimal.ZERO)
                .createdAt(budget.getCreatedAt())
                .build();

        budgetRepositoryPort.save(updatedBudget);

        return true;
    }
}