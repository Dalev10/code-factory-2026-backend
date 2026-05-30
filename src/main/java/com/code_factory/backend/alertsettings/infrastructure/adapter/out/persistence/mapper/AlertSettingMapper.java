package com.code_factory.backend.alertsettings.infrastructure.adapter.out.persistence.mapper;

import com.code_factory.backend.alertsettings.domain.model.UserAlertSetting;
import com.code_factory.backend.alertsettings.infrastructure.adapter.out.persistence.entity.UserAlertSettingEntity;
import org.springframework.stereotype.Component;

@Component
public class AlertSettingMapper {

    public UserAlertSettingEntity toEntity(UserAlertSetting domain) {
        return UserAlertSettingEntity.builder()
                .userId(domain.getUserId())
                .expenseLimit(domain.getExpenseLimit())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public UserAlertSetting toDomain(UserAlertSettingEntity entity) {
        return UserAlertSetting.builder()
                .userId(entity.getUserId())
                .expenseLimit(entity.getExpenseLimit())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
