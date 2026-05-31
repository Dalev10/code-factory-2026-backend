package com.code_factory.backend.alertpreferences.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateAlertPreferenceRequest {

    @NotNull(message = "El campo 'enabled' es obligatorio.")
    private Boolean enabled;
}
