package com.code_factory.backend.alertsettings.infrastructure.adapter.in.web.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AlertSettingResponse {
    private UUID userId;
    private BigDecimal expenseLimit;
    private BigDecimal currentMonthSpent;
    private boolean alertTriggered;
    private LocalDateTime updatedAt;
    private String message;
}
