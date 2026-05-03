package com.code_factory.backend.budgeting.application.service;

import com.code_factory.backend.budgeting.application.port.in.GetBudgetSummaryUseCase;
import com.code_factory.backend.budgeting.application.port.out.ActualExpensePort;
import com.code_factory.backend.budgeting.application.port.out.BudgetRepositoryPort;
import com.code_factory.backend.budgeting.domain.model.Budget;
import com.code_factory.backend.budgeting.domain.model.BudgetSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetBudgetSummaryService implements GetBudgetSummaryUseCase {

    private final BudgetRepositoryPort budgetRepositoryPort;
    private final ActualExpensePort actualExpensePort;

    @Override
    public BudgetSummary getSummary(UUID userId, LocalDate month) {
        // Normalizamos la fecha al primer día del mes para mantener la consistencia
        LocalDate firstOfMonth = month.withDayOfMonth(1);

        // Escenario 2: Usuario sin presupuesto definido
        Budget budget = budgetRepositoryPort.findByUserIdAndMonth(userId, firstOfMonth)
                .orElseThrow(() -> new RuntimeException("No tienes un presupuesto definido para este mes. Por favor, crea uno."));

        // Escenario 1 y 4: Obtener total de gastos (Actualización automática)
        // Delegamos la suma a PostgreSQL a través de nuestro puerto de solo lectura
        BigDecimal totalSpent = actualExpensePort.sumActualExpensesByUserAndMonth(userId, firstOfMonth);

        // Calcular presupuesto restante
        BigDecimal remainingBalance = budget.getExpenseLimit().subtract(totalSpent);

        // Escenario 3: Presupuesto en negativo
        // Si remainingBalance es menor a 0, isExceeded será true
        boolean isExceeded = remainingBalance.compareTo(BigDecimal.ZERO) < 0;

        // Construimos y retornamos el objeto inmutable
        return new BudgetSummary(
                budget.getId(),
                budget.getTotalIncome(),
                budget.getExpenseLimit(),
                totalSpent,
                remainingBalance,
                isExceeded
        );
    }
}