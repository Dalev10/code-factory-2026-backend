package com.code_factory.backend.alertsettings.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AlertSettingStatus(
        UUID userId,
        BigDecimal expenseLimit,
        BigDecimal currentMonthSpent,
        boolean alertTriggered,
        LocalDateTime updatedAt,
        String message
) {}
