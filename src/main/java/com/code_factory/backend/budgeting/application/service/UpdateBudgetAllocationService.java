package com.code_factory.backend.budgeting.application.service;

import com.code_factory.backend.budgeting.application.port.in.UpdateBudgetAllocationCommand;
import com.code_factory.backend.budgeting.application.port.in.UpdateBudgetAllocationUseCase;
import com.code_factory.backend.budgeting.application.port.out.BudgetAllocationRepositoryPort;
import com.code_factory.backend.budgeting.application.port.out.BudgetRepositoryPort;
import com.code_factory.backend.budgeting.domain.model.Budget;
import com.code_factory.backend.budgeting.domain.model.BudgetAllocation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UpdateBudgetAllocationService implements UpdateBudgetAllocationUseCase {

    private final BudgetRepositoryPort budgetRepositoryPort;
    private final BudgetAllocationRepositoryPort budgetAllocationRepositoryPort;

    @Override
    public BudgetAllocation updateAllocation(UpdateBudgetAllocationCommand command) {
        // 1. Validar Presupuesto y Propiedad
        Budget budget = budgetRepositoryPort.findById(command.budgetId())
                .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado"));

        if (!budget.getUserId().equals(command.userId())) {
            throw new RuntimeException("No tienes permiso para modificar este presupuesto");
        }

        // 2. Validar Existencia de la Asignación
        BudgetAllocation existingAllocation = budgetAllocationRepositoryPort
                .findByBudgetIdAndCategoryId(command.budgetId(), command.categoryId())
                .orElseThrow(() -> new RuntimeException("La asignación que intentas editar no existe"));

        // 3. Calcular Nuevo Saldo Disponible (Escenario 3)
        // Saldo Disponible = Límite Total - (Total Asignado - Monto Anterior de esta categoría)
        BigDecimal totalAllocated = budgetAllocationRepositoryPort.sumAmountByBudgetId(command.budgetId());
        BigDecimal totalWithoutCurrent = totalAllocated.subtract(existingAllocation.getAmount());
        BigDecimal recalculatedAvailableBalance = budget.getExpenseLimit().subtract(totalWithoutCurrent);

        if (command.newAmount().compareTo(recalculatedAvailableBalance) > 0) {
            throw new RuntimeException("El nuevo monto supera el saldo disponible del presupuesto");
        }

        // 4. Actualizar y Guardar
        existingAllocation.setAmount(command.newAmount());
        return budgetAllocationRepositoryPort.save(existingAllocation);
    }
}