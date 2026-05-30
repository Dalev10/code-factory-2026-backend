package com.code_factory.backend.alertsettings.infrastructure.adapter.in.web;

import com.code_factory.backend.alertsettings.application.port.in.GetAlertSettingUseCase;
import com.code_factory.backend.alertsettings.application.port.in.SaveAlertSettingUseCase;
import com.code_factory.backend.alertsettings.domain.model.AlertSettingStatus;
import com.code_factory.backend.alertsettings.domain.model.UserAlertSetting;
import com.code_factory.backend.alertsettings.infrastructure.adapter.in.web.dto.AlertSettingResponse;
import com.code_factory.backend.alertsettings.infrastructure.adapter.in.web.dto.SaveAlertSettingRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/alert-settings")
@RequiredArgsConstructor
public class AlertSettingController {

    private final SaveAlertSettingUseCase saveAlertSettingUseCase;
    private final GetAlertSettingUseCase getAlertSettingUseCase;

    @PutMapping("/{userId}")
    public ResponseEntity<AlertSettingResponse> saveOrUpdate(
            @PathVariable UUID userId,
            @Valid @RequestBody SaveAlertSettingRequest request) {

        UserAlertSetting saved = saveAlertSettingUseCase.save(userId, request.getExpenseLimit());

        AlertSettingResponse response = AlertSettingResponse.builder()
                .userId(saved.getUserId())
                .expenseLimit(saved.getExpenseLimit())
                .updatedAt(saved.getUpdatedAt())
                .message("Límite de alerta guardado correctamente.")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<AlertSettingResponse> getSetting(@PathVariable UUID userId) {

        AlertSettingStatus status = getAlertSettingUseCase.getSetting(userId);

        AlertSettingResponse response = AlertSettingResponse.builder()
                .userId(status.userId())
                .expenseLimit(status.expenseLimit())
                .currentMonthSpent(status.currentMonthSpent())
                .alertTriggered(status.alertTriggered())
                .updatedAt(status.updatedAt())
                .message(status.message())
                .build();

        return ResponseEntity.ok(response);
    }
}
