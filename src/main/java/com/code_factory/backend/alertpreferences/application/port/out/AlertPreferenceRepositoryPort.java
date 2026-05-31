package com.code_factory.backend.alertpreferences.application.port.out;

import com.code_factory.backend.alertpreferences.domain.model.AlertType;
import com.code_factory.backend.alertpreferences.domain.model.UserAlertPreference;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlertPreferenceRepositoryPort {
    UserAlertPreference save(UserAlertPreference preference);
    List<UserAlertPreference> findByUserId(UUID userId);
    Optional<UserAlertPreference> findByUserIdAndAlertType(UUID userId, AlertType alertType);
}
