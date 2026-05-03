package com.code_factory.backend.budgeting.application.service;

import com.code_factory.backend.budgeting.application.port.in.UpdateBudgetCommand;
import com.code_factory.backend.budgeting.application.port.in.UpdateBudgetUseCase;
import com.code_factory.backend.budgeting.application.port.out.BudgetRepositoryPort;
import com.code_factory.backend.budgeting.application.port.out.BudgetAllocationRepositoryPort;
import com.code_factory.backend.budgeting.domain.model.Budget;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UpdateBudgetService implements UpdateBudgetUseCase {

    private final BudgetRepositoryPort repository;
    private final BudgetAllocationRepositoryPort allocationRepositoryPort; // Añadido para validar cruces

    @Override
    public Budget updateBudget(UpdateBudgetCommand command) {
        // 1. Validar Presupuesto y Propiedad
        Budget budget = repository.findById(command.budgetId())
                .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado"));

        if (!budget.getUserId().equals(command.userId())) {
            throw new RuntimeException("No tienes permiso para modificar este presupuesto");
        }

        // 2. Validar que el mes no se haya cerrado
        if (budget.getMonth().isBefore(LocalDate.now().withDayOfMonth(1))) {
            throw new RuntimeException("No puedes modificar un presupuesto de un mes cerrado");
        }

        // 3. Validar consistencia contra Asignaciones existentes (¡NUEVO Y CRÍTICO!)
        BigDecimal totalAllocated = allocationRepositoryPort.sumAmountByBudgetId(command.budgetId());
        if (command.newLimit().compareTo(totalAllocated) < 0) {
            throw new RuntimeException("El nuevo límite de gastos ($" + command.newLimit() + 
                ") no puede ser menor a lo que ya tienes asignado en categorías ($" + totalAllocated + ")");
        }

        // 4. Actualizar y Guardar
        Budget updatedBudget = Budget.builder()
                .id(budget.getId())
                .userId(budget.getUserId())
                .month(budget.getMonth())
                .totalIncome(command.newTotalIncome())
                .expenseLimit(command.newLimit())
                .createdAt(budget.getCreatedAt())
                .build();

        return repository.save(updatedBudget);
    }
}