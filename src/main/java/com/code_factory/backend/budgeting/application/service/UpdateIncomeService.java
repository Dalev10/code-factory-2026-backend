package com.code_factory.backend.budgeting.application.service;

import com.code_factory.backend.budgeting.application.port.in.UpdateIncomeUseCase;
import com.code_factory.backend.budgeting.application.port.out.BudgetRepositoryPort;
import com.code_factory.backend.budgeting.domain.model.Budget;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateIncomeService implements UpdateIncomeUseCase {

    private final BudgetRepositoryPort repository;

    @Override
    public void update(UUID userId, BigDecimal newIncome) {

        LocalDate month = LocalDate.now().withDayOfMonth(1);

        Budget budget = repository.findByUserIdAndMonth(userId, month)
                .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado"));

        Budget updated = budget.updateIncome(newIncome);

        repository.save(updated);
    }
}
