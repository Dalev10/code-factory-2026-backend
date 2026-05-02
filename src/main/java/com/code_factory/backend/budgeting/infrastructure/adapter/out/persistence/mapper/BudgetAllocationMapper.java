// /src/main/java/com/code_factory/backend/budgeting/infrastructure/adapter/out/persistence/mapper/BudgetAllocationMapper.java
package com.code_factory.backend.budgeting.infrastructure.adapter.out.persistence.mapper;

import com.code_factory.backend.budgeting.domain.model.BudgetAllocation;
import com.code_factory.backend.budgeting.infrastructure.adapter.out.persistence.entity.BudgetAllocationEntity;
import org.springframework.stereotype.Component;

@Component
public class BudgetAllocationMapper {

    public BudgetAllocationEntity toEntity(BudgetAllocation domain) {
        if (domain == null) return null;
        return BudgetAllocationEntity.builder()
                .id(domain.getId())
                .budgetId(domain.getBudgetId())
                .categoryId(domain.getCategoryId())
                .amount(domain.getAmount())
                .build();
    }

    public BudgetAllocation toDomain(BudgetAllocationEntity entity) {
        if (entity == null) return null;
        return BudgetAllocation.builder()
                .id(entity.getId())
                .budgetId(entity.getBudgetId())
                .categoryId(entity.getCategoryId())
                .amount(entity.getAmount())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}