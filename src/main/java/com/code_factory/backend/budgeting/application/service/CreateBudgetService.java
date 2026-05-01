package com.code_factory.backend.budgeting.application.service;

import com.code_factory.backend.budgeting.application.port.in.CreateBudgetCommand;
import com.code_factory.backend.budgeting.application.port.in.CreateBudgetUseCase;
import com.code_factory.backend.budgeting.application.port.out.BudgetRepositoryPort;
import com.code_factory.backend.budgeting.domain.model.Budget;
import com.code_factory.backend.identity.application.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateBudgetService implements CreateBudgetUseCase {

    private final BudgetRepositoryPort budgetRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public Budget createBudget(CreateBudgetCommand command) {
        userRepositoryPort.findById(command.userId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        LocalDate firstOfMonth = command.month().withDayOfMonth(1);

        budgetRepositoryPort.findByUserIdAndMonth(command.userId(), firstOfMonth)
                .ifPresent(b -> { throw new RuntimeException("Ya existe un presupuesto para este mes"); });

        Budget budget = Budget.builder()
                .id(UUID.randomUUID())
                .userId(command.userId())
                .month(firstOfMonth)
                .totalIncome(command.totalIncome())
                .expenseLimit(command.expenseLimit())
                .build();

        return budgetRepositoryPort.save(budget);
    }
}
