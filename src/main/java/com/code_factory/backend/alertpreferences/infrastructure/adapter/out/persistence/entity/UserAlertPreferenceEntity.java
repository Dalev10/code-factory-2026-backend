package com.code_factory.backend.alertpreferences.infrastructure.adapter.out.persistence.entity;

import com.code_factory.backend.alertpreferences.domain.model.AlertType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "user_alert_preferences",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "alert_type"})
)
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class UserAlertPreferenceEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "alert_type", nullable = false, length = 30)
    private AlertType alertType;

    @Column(nullable = false)
    private boolean enabled;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
