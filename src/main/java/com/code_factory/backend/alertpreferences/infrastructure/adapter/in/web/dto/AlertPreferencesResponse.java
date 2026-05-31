package com.code_factory.backend.alertpreferences.infrastructure.adapter.in.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class AlertPreferencesResponse {
    private UUID userId;
    private List<AlertPreferenceItemResponse> preferences;
}
