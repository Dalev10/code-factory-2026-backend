package com.code_factory.backend.alertsettings.application.port.in;

import com.code_factory.backend.alertsettings.domain.model.UserAlertSetting;

import java.math.BigDecimal;
import java.util.UUID;

public interface SaveAlertSettingUseCase {
    UserAlertSetting save(UUID userId, BigDecimal expenseLimit);
}
