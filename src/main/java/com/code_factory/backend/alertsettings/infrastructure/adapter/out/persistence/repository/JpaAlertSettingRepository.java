package com.code_factory.backend.alertsettings.infrastructure.adapter.out.persistence.repository;

import com.code_factory.backend.alertsettings.infrastructure.adapter.out.persistence.entity.UserAlertSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaAlertSettingRepository extends JpaRepository<UserAlertSettingEntity, UUID> {
}
