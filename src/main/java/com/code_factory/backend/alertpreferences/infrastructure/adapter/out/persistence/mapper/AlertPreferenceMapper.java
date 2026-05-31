package com.code_factory.backend.alertpreferences.infrastructure.adapter.out.persistence.mapper;

import com.code_factory.backend.alertpreferences.domain.model.UserAlertPreference;
import com.code_factory.backend.alertpreferences.infrastructure.adapter.out.persistence.entity.UserAlertPreferenceEntity;
import org.springframework.stereotype.Component;

@Component
public class AlertPreferenceMapper {

    public UserAlertPreference toDomain(UserAlertPreferenceEntity entity) {
        return UserAlertPreference.builder()
                .userId(entity.getUserId())
                .alertType(entity.getAlertType())
                .enabled(entity.isEnabled())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
