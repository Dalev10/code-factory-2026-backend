package com.code_factory.backend.alertpreferences.infrastructure.adapter.out.persistence.repository;

import com.code_factory.backend.alertpreferences.domain.model.AlertType;
import com.code_factory.backend.alertpreferences.infrastructure.adapter.out.persistence.entity.UserAlertPreferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaAlertPreferenceRepository extends JpaRepository<UserAlertPreferenceEntity, UUID> {
    List<UserAlertPreferenceEntity> findByUserId(UUID userId);
    Optional<UserAlertPreferenceEntity> findByUserIdAndAlertType(UUID userId, AlertType alertType);
}
