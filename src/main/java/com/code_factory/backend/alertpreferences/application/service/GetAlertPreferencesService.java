package com.code_factory.backend.alertpreferences.application.service;

import com.code_factory.backend.alertpreferences.application.port.in.GetAlertPreferencesUseCase;
import com.code_factory.backend.alertpreferences.application.port.out.AlertPreferenceRepositoryPort;
import com.code_factory.backend.alertpreferences.domain.model.AlertType;
import com.code_factory.backend.alertpreferences.domain.model.UserAlertPreference;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAlertPreferencesService implements GetAlertPreferencesUseCase {

    private final AlertPreferenceRepositoryPort alertPreferenceRepositoryPort;

    @Override
    public List<UserAlertPreference> getPreferences(UUID userId) {
        Map<AlertType, UserAlertPreference> persisted = alertPreferenceRepositoryPort
                .findByUserId(userId)
                .stream()
                .collect(Collectors.toMap(UserAlertPreference::getAlertType, p -> p));

        return Arrays.stream(AlertType.values())
                .map(type -> persisted.getOrDefault(type, defaultPreference(userId, type)))
                .collect(Collectors.toList());
    }

    private UserAlertPreference defaultPreference(UUID userId, AlertType alertType) {
        return UserAlertPreference.builder()
                .userId(userId)
                .alertType(alertType)
                .enabled(true)
                .build();
    }
}
