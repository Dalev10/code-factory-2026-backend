package com.code_factory.backend.alertsettings.application.port.in;

import com.code_factory.backend.alertsettings.domain.model.AlertSettingStatus;

import java.util.UUID;

public interface GetAlertSettingUseCase {
    AlertSettingStatus getSetting(UUID userId);
}
