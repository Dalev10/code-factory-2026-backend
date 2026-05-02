package com.code_factory.backend.budgeting.application.service;

import com.code_factory.backend.budgeting.application.port.in.GetBudgetProgressUseCase;
import com.code_factory.backend.budgeting.application.port.in.GetBudgetSummaryUseCase;
import com.code_factory.backend.budgeting.application.port.out.ActualExpensePort;
import com.code_factory.backend.budgeting.application.port.out.BudgetAllocationRepositoryPort;
import com.code_factory.backend.classification.application.port.out.CategoryRepositoryPort;
import com.code_factory.backend.budgeting.domain.model.BudgetProgressReport;
import com.code_factory.backend.budgeting.domain.model.BudgetSummary;
import com.code_factory.backend.budgeting.domain.model.CategoryProgress;
import com.code_factory.backend.budgeting.domain.model.BudgetAllocation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetBudgetProgressService implements GetBudgetProgressUseCase {

    // Reutilizamos el caso de uso global de la HU 5.2.2 para no duplicar código
    private final GetBudgetSummaryUseCase getBudgetSummaryUseCase;
    private final BudgetAllocationRepositoryPort budgetAllocationRepositoryPort;
    private final ActualExpensePort actualExpensePort;
    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public BudgetProgressReport getProgress(UUID userId, LocalDate month) {
        LocalDate firstOfMonth = month.withDayOfMonth(1);

        // 1. Obtener el resumen global (Valida si existe el presupuesto por debajo)
        BudgetSummary globalSummary = getBudgetSummaryUseCase.getSummary(userId, firstOfMonth);

        // 2. Obtener las asignaciones planeadas para este presupuesto
        List<BudgetAllocation> allocations = budgetAllocationRepositoryPort.findByBudgetId(globalSummary.budgetId());

        // 3. Obtener los gastos reales agrupados por categoría (Nuestra consulta optimizada)
        Map<UUID, BigDecimal> expensesByCategory = actualExpensePort.getExpensesByCategory(userId, firstOfMonth);

        // 4. Construir el detalle cruzando Asignaciones vs Gastos Reales
        List<CategoryProgress> categoryDetails = allocations.stream().map(allocation -> {
            UUID catId = allocation.getCategoryId();
            BigDecimal allocated = allocation.getAmount();

            // Si no hay gastos en el mapa para esta categoría, asumimos 0
            BigDecimal spent = expensesByCategory.getOrDefault(catId, BigDecimal.ZERO);

            // Calcular saldo restante para esta categoría específica
            BigDecimal remaining = allocated.subtract(spent);

            // Calcular porcentaje consumido (previniendo división por cero)
            double percentage = 0.0;
            if (allocated.compareTo(BigDecimal.ZERO) > 0) {
                percentage = spent.divide(allocated, 4, RoundingMode.HALF_UP)
                                  .multiply(new BigDecimal("100"))
                                  .doubleValue();
            }

            // Rescatar el nombre de la categoría consultando al puerto de clasificación
            String categoryName = categoryRepositoryPort.findById(catId)
                    .map(c -> c.getName())
                    .orElse("Categoría Desconocida");

            return new CategoryProgress(catId, categoryName, allocated, spent, remaining, percentage);
            
        }).collect(Collectors.toList());

        // 5. Retornar el reporte consolidado
        return new BudgetProgressReport(globalSummary, categoryDetails);
    }
}