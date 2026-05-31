package com.code_factory.backend.alertpreferences.application.port.in;

import com.code_factory.backend.alertpreferences.domain.model.AlertType;
import com.code_factory.backend.alertpreferences.domain.model.UserAlertPreference;

import java.util.UUID;

public interface UpdateAlertPreferenceUseCase {
    UserAlertPreference update(UUID userId, AlertType alertType, boolean enabled);
}
