package com.code_factory.backend.alertsettings.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_alert_settings")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class UserAlertSettingEntity {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "expense_limit", nullable = false, precision = 15, scale = 2)
    private BigDecimal expenseLimit;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
