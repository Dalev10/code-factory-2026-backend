package com.code_factory.backend.alertpreferences.infrastructure.adapter.out.persistence.adapter;

import com.code_factory.backend.alertpreferences.application.port.out.AlertPreferenceRepositoryPort;
import com.code_factory.backend.alertpreferences.domain.model.AlertType;
import com.code_factory.backend.alertpreferences.domain.model.UserAlertPreference;
import com.code_factory.backend.alertpreferences.infrastructure.adapter.out.persistence.entity.UserAlertPreferenceEntity;
import com.code_factory.backend.alertpreferences.infrastructure.adapter.out.persistence.mapper.AlertPreferenceMapper;
import com.code_factory.backend.alertpreferences.infrastructure.adapter.out.persistence.repository.JpaAlertPreferenceRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AlertPreferencePersistenceAdapter implements AlertPreferenceRepositoryPort {

    private final JpaAlertPreferenceRepository repository;
    private final AlertPreferenceMapper mapper;

    @Override
    public UserAlertPreference save(UserAlertPreference preference) {
        UUID entityId = repository
                .findByUserIdAndAlertType(preference.getUserId(), preference.getAlertType())
                .map(UserAlertPreferenceEntity::getId)
                .orElse(UUID.randomUUID());

        UserAlertPreferenceEntity entity = UserAlertPreferenceEntity.builder()
                .id(entityId)
                .userId(preference.getUserId())
                .alertType(preference.getAlertType())
                .enabled(preference.isEnabled())
                .build();

        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public List<UserAlertPreference> findByUserId(UUID userId) {
        return repository.findByUserId(userId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<UserAlertPreference> findByUserIdAndAlertType(UUID userId, AlertType alertType) {
        return repository.findByUserIdAndAlertType(userId, alertType)
                .map(mapper::toDomain);
    }
}
