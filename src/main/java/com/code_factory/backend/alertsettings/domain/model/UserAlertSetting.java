package com.code_factory.backend.alertsettings.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class UserAlertSetting {
    private final UUID userId;
    private final BigDecimal expenseLimit;
    private final LocalDateTime updatedAt;
}
