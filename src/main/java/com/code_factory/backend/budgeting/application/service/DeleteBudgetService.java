package com.code_factory.backend.budgeting.application.service;

import com.code_factory.backend.budgeting.application.port.out.BudgetRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteBudgetService {

    private final BudgetRepositoryPort budgetRepositoryPort;

    public void deleteBudget(UUID userId, LocalDate month) {

        LocalDate firstOfMonth = month.withDayOfMonth(1);

        var budget = budgetRepositoryPort
                .findByUserIdAndMonth(userId, firstOfMonth)
                .orElseThrow(() -> new RuntimeException("No existe un presupuesto para este mes"));

        budgetRepositoryPort.delete(budget);
    }
}