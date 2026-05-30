package com.code_factory.backend.alertsettings.application.service;

import com.code_factory.backend.alertsettings.application.port.in.SaveAlertSettingUseCase;
import com.code_factory.backend.alertsettings.application.port.out.AlertSettingRepositoryPort;
import com.code_factory.backend.alertsettings.domain.model.UserAlertSetting;
import com.code_factory.backend.identity.application.port.out.UserRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaveAlertSettingService implements SaveAlertSettingUseCase {

    private final AlertSettingRepositoryPort alertSettingRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public UserAlertSetting save(UUID userId, BigDecimal expenseLimit) {
        if (expenseLimit == null || expenseLimit.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El límite de gasto debe ser mayor a cero.");
        }

        userRepositoryPort.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        UserAlertSetting setting = UserAlertSetting.builder()
                .userId(userId)
                .expenseLimit(expenseLimit)
                .build();

        return alertSettingRepositoryPort.save(setting);
    }
}
