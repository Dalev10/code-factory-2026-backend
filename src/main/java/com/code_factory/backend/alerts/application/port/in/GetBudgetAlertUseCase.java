package com.code_factory.backend.alerts.application.port.in;

import com.code_factory.backend.alerts.domain.model.BudgetAlert;

import java.time.LocalDate;
import java.util.UUID;

public interface GetBudgetAlertUseCase {
    BudgetAlert getAlert(UUID userId, LocalDate month, double thresholdPercentage);
}
