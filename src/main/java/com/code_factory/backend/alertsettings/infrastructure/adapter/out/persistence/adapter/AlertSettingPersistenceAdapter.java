package com.code_factory.backend.alertsettings.infrastructure.adapter.out.persistence.adapter;

import com.code_factory.backend.alertsettings.application.port.out.AlertSettingRepositoryPort;
import com.code_factory.backend.alertsettings.domain.model.UserAlertSetting;
import com.code_factory.backend.alertsettings.infrastructure.adapter.out.persistence.mapper.AlertSettingMapper;
import com.code_factory.backend.alertsettings.infrastructure.adapter.out.persistence.repository.JpaAlertSettingRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AlertSettingPersistenceAdapter implements AlertSettingRepositoryPort {

    private final JpaAlertSettingRepository repository;
    private final AlertSettingMapper mapper;

    @Override
    public UserAlertSetting save(UserAlertSetting setting) {
        return mapper.toDomain(repository.save(mapper.toEntity(setting)));
    }

    @Override
    public Optional<UserAlertSetting> findByUserId(UUID userId) {
        return repository.findById(userId).map(mapper::toDomain);
    }
}
