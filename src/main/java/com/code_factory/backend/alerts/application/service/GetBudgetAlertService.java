package com.code_factory.backend.alerts.application.service;

import com.code_factory.backend.alerts.application.port.in.GetBudgetAlertUseCase;
import com.code_factory.backend.alerts.domain.model.AlertLevel;
import com.code_factory.backend.alerts.domain.model.BudgetAlert;
import com.code_factory.backend.budgeting.application.port.in.GetBudgetSummaryUseCase;
import com.code_factory.backend.budgeting.domain.model.BudgetSummary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetBudgetAlertService implements GetBudgetAlertUseCase {

    private static final double MIN_THRESHOLD = 1.0;
    private static final double MAX_THRESHOLD = 100.0;

    private final GetBudgetSummaryUseCase getBudgetSummaryUseCase;

    @Override
    public BudgetAlert getAlert(UUID userId, LocalDate month, double thresholdPercentage) {
        if (thresholdPercentage < MIN_THRESHOLD || thresholdPercentage > MAX_THRESHOLD) {
            throw new IllegalArgumentException(
                    String.format("El umbral debe estar entre %.0f%% y %.0f%%.", MIN_THRESHOLD, MAX_THRESHOLD));
        }

        BudgetSummary summary;
        try {
            summary = getBudgetSummaryUseCase.getSummary(userId, month.withDayOfMonth(1));
        } catch (Exception e) {
            return new BudgetAlert(
                    false,
                    AlertLevel.NONE,
                    0.0,
                    null,
                    null,
                    null,
                    thresholdPercentage,
                    "No tienes un presupuesto activo para el mes seleccionado. Crea uno para empezar a recibir alertas."
            );
        }

        double consumed = calculateConsumedPercentage(summary.totalSpent(), summary.expenseLimit());

        AlertLevel level = resolveAlertLevel(consumed, thresholdPercentage);
        String message = buildMessage(level, consumed, summary.remainingBalance());

        return new BudgetAlert(
                true,
                level,
                consumed,
                summary.expenseLimit(),
                summary.totalSpent(),
                summary.remainingBalance(),
                thresholdPercentage,
                message
        );
    }

    private double calculateConsumedPercentage(BigDecimal totalSpent, BigDecimal expenseLimit) {
        if (expenseLimit == null || expenseLimit.compareTo(BigDecimal.ZERO) == 0) {
            return 0.0;
        }
        return totalSpent
                .divide(expenseLimit, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"))
                .doubleValue();
    }

    private AlertLevel resolveAlertLevel(double consumed, double threshold) {
        if (consumed >= 100.0) {
            return AlertLevel.EXCEEDED;
        }
        if (consumed >= threshold) {
            return AlertLevel.APPROACHING;
        }
        return AlertLevel.NONE;
    }

    private String buildMessage(AlertLevel level, double consumed, BigDecimal remainingBalance) {
        return switch (level) {
            case EXCEEDED -> String.format(
                    "Presupuesto excedido: llevas un %.0f%% consumido. Has superado tu límite en $%.2f.",
                    consumed, remainingBalance.abs());
            case APPROACHING -> String.format(
                    "Estás cerca de tu límite: llevas un %.0f%% consumido. Te quedan $%.2f disponibles.",
                    consumed, remainingBalance);
            case NONE -> String.format(
                    "Tu presupuesto está bajo control: llevas un %.0f%% consumido. Te quedan $%.2f disponibles.",
                    consumed, remainingBalance);
        };
    }
}
