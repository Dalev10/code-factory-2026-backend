package com.code_factory.backend.alerts.infrastructure.adapter.in.web;

import com.code_factory.backend.alerts.application.port.in.GetBudgetAlertUseCase;
import com.code_factory.backend.alerts.domain.model.BudgetAlert;
import com.code_factory.backend.alerts.infrastructure.adapter.in.web.dto.BudgetAlertResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/budget-alerts")
@RequiredArgsConstructor
public class BudgetAlertController {

    private static final double DEFAULT_THRESHOLD = 80.0;

    private final GetBudgetAlertUseCase getBudgetAlertUseCase;

    @GetMapping("/{userId}")
    public ResponseEntity<BudgetAlertResponse> getAlert(
            @PathVariable UUID userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate month,
            @RequestParam(required = false) Double threshold) {

        LocalDate targetMonth = (month != null) ? month : LocalDate.now();
        double targetThreshold = (threshold != null) ? threshold : DEFAULT_THRESHOLD;

        BudgetAlert alert = getBudgetAlertUseCase.getAlert(userId, targetMonth, targetThreshold);

        BudgetAlertResponse response = BudgetAlertResponse.builder()
                .hasBudget(alert.hasBudget())
                .alertLevel(alert.alertLevel().name())
                .consumedPercentage(alert.consumedPercentage())
                .expenseLimit(alert.expenseLimit())
                .totalSpent(alert.totalSpent())
                .remainingBalance(alert.remainingBalance())
                .thresholdPercentage(alert.thresholdPercentage())
                .message(alert.message())
                .build();

        return ResponseEntity.ok(response);
    }
}
