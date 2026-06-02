package com.code_factory.backend.alertpreferences.application.service;

import com.code_factory.backend.alertpreferences.application.port.in.UpdateAlertPreferenceUseCase;
import com.code_factory.backend.alertpreferences.application.port.out.AlertPreferenceRepositoryPort;
import com.code_factory.backend.alertpreferences.domain.model.AlertType;
import com.code_factory.backend.alertpreferences.domain.model.UserAlertPreference;
import com.code_factory.backend.identity.application.port.out.UserRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateAlertPreferenceService implements UpdateAlertPreferenceUseCase {

    private final AlertPreferenceRepositoryPort alertPreferenceRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public UserAlertPreference update(UUID userId, AlertType alertType, boolean enabled) {

        userRepositoryPort.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        UserAlertPreference preference = UserAlertPreference.builder()
                .userId(userId)
                .alertType(alertType)
                .enabled(enabled)
                .updatedAt(LocalDateTime.now())
                .build();

        return alertPreferenceRepositoryPort.save(preference);
    }
}
