package com.code_factory.backend.alertpreferences.infrastructure.adapter.in.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AlertPreferenceItemResponse {
    private String alertType;
    private boolean enabled;
    private LocalDateTime updatedAt;
}
