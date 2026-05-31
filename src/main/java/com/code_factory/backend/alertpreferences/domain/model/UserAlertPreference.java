package com.code_factory.backend.alertpreferences.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class UserAlertPreference {
    private final UUID userId;
    private final AlertType alertType;
    private final boolean enabled;
    private final LocalDateTime updatedAt;
}
