package com.code_factory.backend.alertpreferences.application.port.in;

import com.code_factory.backend.alertpreferences.domain.model.UserAlertPreference;

import java.util.List;
import java.util.UUID;

public interface GetAlertPreferencesUseCase {
    List<UserAlertPreference> getPreferences(UUID userId);
}
