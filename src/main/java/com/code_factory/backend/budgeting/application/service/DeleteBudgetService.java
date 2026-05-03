package com.code_factory.backend.budgeting.application.service;

import com.code_factory.backend.budgeting.application.port.in.DeleteBudgetCommand;
import com.code_factory.backend.budgeting.application.port.in.DeleteBudgetUseCase;
import com.code_factory.backend.budgeting.application.port.out.BudgetRepositoryPort;
import com.code_factory.backend.budgeting.domain.model.Budget;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteBudgetService implements DeleteBudgetUseCase {

    private final BudgetRepositoryPort budgetRepositoryPort;

    @Override
    public void deleteBudget(DeleteBudgetCommand command) {

        Budget budget = budgetRepositoryPort.findById(command.id())
                .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado"));

        if (!budget.getUserId().equals(command.userId())) {
            throw new RuntimeException("No tienes permiso para eliminar este presupuesto");
        }

        budgetRepositoryPort.deleteById(command.id());
    }
}