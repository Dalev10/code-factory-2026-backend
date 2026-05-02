package com.code_factory.backend.budgeting.application.service;

import com.code_factory.backend.budgeting.application.port.in.UpdateBudgetUseCase;
import com.code_factory.backend.budgeting.application.port.out.BudgetRepositoryPort;
import com.code_factory.backend.budgeting.domain.model.Budget;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateBudgetService implements UpdateBudgetUseCase {

    private final BudgetRepositoryPort repository;

    @Override
    public void updateExpenseLimit(UUID userId, BigDecimal newLimit) {

        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);

        Budget budget = repository
                .findByUserIdAndMonth(userId, currentMonth)
                .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado"));

        Budget updated = budget.updateExpenseLimit(newLimit);

        repository.save(updated);
    }
}