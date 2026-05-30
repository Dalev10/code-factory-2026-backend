package com.code_factory.backend.alertsettings.application.service;

import com.code_factory.backend.alertsettings.application.port.in.GetAlertSettingUseCase;
import com.code_factory.backend.alertsettings.application.port.out.AlertSettingRepositoryPort;
import com.code_factory.backend.alertsettings.domain.model.AlertSettingStatus;
import com.code_factory.backend.alertsettings.domain.model.UserAlertSetting;
import com.code_factory.backend.budgeting.application.port.out.ActualExpensePort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetAlertSettingService implements GetAlertSettingUseCase {

    private final AlertSettingRepositoryPort alertSettingRepositoryPort;
    private final ActualExpensePort actualExpensePort;

    @Override
    public AlertSettingStatus getSetting(UUID userId) {
        UserAlertSetting setting = alertSettingRepositoryPort.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException(
                        "No tienes un límite de alerta configurado. Usa PUT /api/alert-settings/" + userId + " para configurarlo."));

        BigDecimal currentMonthSpent = actualExpensePort
                .sumActualExpensesByUserAndMonth(userId, LocalDate.now().withDayOfMonth(1));

        boolean alertTriggered = currentMonthSpent.compareTo(setting.getExpenseLimit()) > 0;

        String message = alertTriggered
                ? String.format("Alerta: tus gastos del mes ($%.2f) superaron el límite configurado de $%.2f.",
                        currentMonthSpent, setting.getExpenseLimit())
                : String.format("Tus gastos del mes ($%.2f) están dentro del límite configurado de $%.2f.",
                        currentMonthSpent, setting.getExpenseLimit());

        return new AlertSettingStatus(
                setting.getUserId(),
                setting.getExpenseLimit(),
                currentMonthSpent,
                alertTriggered,
                setting.getUpdatedAt(),
                message
        );
    }
}
