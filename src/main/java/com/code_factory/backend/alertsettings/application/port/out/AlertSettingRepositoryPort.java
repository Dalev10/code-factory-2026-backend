package com.code_factory.backend.alertsettings.application.port.out;

import com.code_factory.backend.alertsettings.domain.model.UserAlertSetting;

import java.util.Optional;
import java.util.UUID;

public interface AlertSettingRepositoryPort {
    UserAlertSetting save(UserAlertSetting setting);
    Optional<UserAlertSetting> findByUserId(UUID userId);
}
