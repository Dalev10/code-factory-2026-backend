package com.code_factory.backend.alertpreferences.infrastructure.adapter.in.web;

import com.code_factory.backend.alertpreferences.application.port.in.GetAlertPreferencesUseCase;
import com.code_factory.backend.alertpreferences.application.port.in.UpdateAlertPreferenceUseCase;
import com.code_factory.backend.alertpreferences.domain.model.AlertType;
import com.code_factory.backend.alertpreferences.domain.model.UserAlertPreference;
import com.code_factory.backend.alertpreferences.infrastructure.adapter.in.web.dto.AlertPreferenceItemResponse;
import com.code_factory.backend.alertpreferences.infrastructure.adapter.in.web.dto.AlertPreferencesResponse;
import com.code_factory.backend.alertpreferences.infrastructure.adapter.in.web.dto.UpdateAlertPreferenceRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/alert-preferences")
@RequiredArgsConstructor
public class AlertPreferenceController {

    private final UpdateAlertPreferenceUseCase updateAlertPreferenceUseCase;
    private final GetAlertPreferencesUseCase getAlertPreferencesUseCase;

    @PutMapping("/{userId}/{alertType}")
    public ResponseEntity<AlertPreferenceItemResponse> update(
            @PathVariable UUID userId,
            @PathVariable AlertType alertType,
            @Valid @RequestBody UpdateAlertPreferenceRequest request) {

        UserAlertPreference updated = updateAlertPreferenceUseCase.update(userId, alertType, request.getEnabled());

        AlertPreferenceItemResponse response = AlertPreferenceItemResponse.builder()
                .alertType(updated.getAlertType().name())
                .enabled(updated.isEnabled())
                .updatedAt(updated.getUpdatedAt())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<AlertPreferencesResponse> getAll(@PathVariable UUID userId) {

        List<AlertPreferenceItemResponse> items = getAlertPreferencesUseCase.getPreferences(userId)
                .stream()
                .map(p -> AlertPreferenceItemResponse.builder()
                        .alertType(p.getAlertType().name())
                        .enabled(p.isEnabled())
                        .updatedAt(p.getUpdatedAt())
                        .build())
                .toList();

        AlertPreferencesResponse response = AlertPreferencesResponse.builder()
                .userId(userId)
                .preferences(items)
                .build();

        return ResponseEntity.ok(response);
    }
}
