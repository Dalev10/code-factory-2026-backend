package com.code_factory.backend.budgeting.application.service;

import com.code_factory.backend.budgeting.application.port.in.CreateBudgetAllocationCommand;
import com.code_factory.backend.budgeting.application.port.in.CreateBudgetAllocationUseCase;
import com.code_factory.backend.budgeting.application.port.out.BudgetAllocationRepositoryPort;
import com.code_factory.backend.budgeting.application.port.out.BudgetRepositoryPort;
import com.code_factory.backend.budgeting.domain.model.Budget;
import com.code_factory.backend.budgeting.domain.model.BudgetAllocation;
import com.code_factory.backend.classification.application.port.out.CategoryRepositoryPort;
import com.code_factory.backend.classification.domain.model.Category;
import com.code_factory.backend.classification.domain.model.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateBudgetAllocationService implements CreateBudgetAllocationUseCase {

    private final BudgetRepositoryPort budgetRepositoryPort;
    private final CategoryRepositoryPort categoryRepositoryPort;
    private final BudgetAllocationRepositoryPort budgetAllocationRepositoryPort;

    @Override
    public BudgetAllocation createAllocation(CreateBudgetAllocationCommand command) {
        // 1. Validar Presupuesto y Propiedad (Seguridad)
        Budget budget = budgetRepositoryPort.findById(command.budgetId())
                .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado"));
        
        if (!budget.getUserId().equals(command.userId())) {
            throw new RuntimeException("No tienes permiso para modificar este presupuesto");
        }

        // 2. Validar Categoría
        Category category = categoryRepositoryPort.findById(command.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
                
        if (category.getType() != CategoryType.EXPENSE) {
            throw new RuntimeException("Solo se pueden asignar categorías de tipo GASTO");
        }

        // 3. Validar Duplicidad
        budgetAllocationRepositoryPort.findByBudgetIdAndCategoryId(command.budgetId(), command.categoryId())
                .ifPresent(a -> { throw new RuntimeException("Ya existe una asignación para esta categoría en el presupuesto"); });

        // 4. Calcular Saldo Disponible (Escenario 2)
        BigDecimal totalAllocated = budgetAllocationRepositoryPort.sumAmountByBudgetId(command.budgetId());
        BigDecimal availableBalance = budget.getExpenseLimit().subtract(totalAllocated);
        
        if (command.amount().compareTo(availableBalance) > 0) {
            throw new RuntimeException("El monto supera el saldo disponible del presupuesto");
        }

        // 5. Crear y Guardar (Escenario 1)
        BudgetAllocation allocation = BudgetAllocation.builder()
                .id(UUID.randomUUID())
                .budgetId(command.budgetId())
                .categoryId(command.categoryId())
                .amount(command.amount())
                .build();

        return budgetAllocationRepositoryPort.save(allocation);
    }
}