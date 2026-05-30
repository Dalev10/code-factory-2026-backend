package com.code_factory.backend.savings.application.service;

import com.code_factory.backend.budgeting.application.port.in.GetBudgetProgressUseCase;
import com.code_factory.backend.budgeting.domain.model.BudgetProgressReport;
import com.code_factory.backend.budgeting.domain.model.CategoryProgress;
import com.code_factory.backend.savings.application.port.in.GetSavingsRecommendationsUseCase;
import com.code_factory.backend.savings.application.port.out.TransactionCountPort;
import com.code_factory.backend.savings.domain.model.SavingRecommendation;
import com.code_factory.backend.savings.domain.model.SavingsReport;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetSavingsRecommendationsService implements GetSavingsRecommendationsUseCase {

    private static final int MINIMUM_TRANSACTIONS = 3;
    private static final double ALERT_THRESHOLD_PERCENTAGE = 80.0;

    private final TransactionCountPort transactionCountPort;
    private final GetBudgetProgressUseCase getBudgetProgressUseCase;

    @Override
    public SavingsReport getRecommendations(UUID userId, LocalDate month) {
        long transactionCount = transactionCountPort.countByUserId(userId);
        if (transactionCount < MINIMUM_TRANSACTIONS) {
            return new SavingsReport(
                    false,
                    "Necesitas registrar al menos 3 transacciones para recibir recomendaciones personalizadas de ahorro.",
                    List.of()
            );
        }

        BudgetProgressReport progress;
        try {
            progress = getBudgetProgressUseCase.getProgress(userId, month.withDayOfMonth(1));
        } catch (Exception e) {
            return new SavingsReport(
                    false,
                    "No tienes un presupuesto activo para el mes seleccionado. Crea un presupuesto para recibir recomendaciones.",
                    List.of()
            );
        }

        List<SavingRecommendation> recommendations = progress.categoryDetails().stream()
                .filter(cp -> cp.progressPercentage() >= ALERT_THRESHOLD_PERCENTAGE)
                .map(cp -> new SavingRecommendation(
                        cp.categoryId(),
                        cp.categoryName(),
                        cp.allocatedAmount(),
                        cp.spentAmount(),
                        cp.progressPercentage(),
                        buildMessage(cp)
                ))
                .collect(Collectors.toList());

        return new SavingsReport(true, null, recommendations);
    }

    private String buildMessage(CategoryProgress cp) {
        if (cp.progressPercentage() >= 100.0) {
            return String.format(
                    "Has superado el límite presupuestado en la categoría '%s' (%.0f%% consumido). Reduce estos gastos el próximo mes.",
                    cp.categoryName(), cp.progressPercentage());
        }
        return String.format(
                "Llevas un %.0f%% del presupuesto asignado en '%s'. Te quedan $%.2f disponibles. Considera reducir estos gastos para mantenerte en el límite.",
                cp.progressPercentage(), cp.categoryName(), cp.remainingAmount());
    }
}
