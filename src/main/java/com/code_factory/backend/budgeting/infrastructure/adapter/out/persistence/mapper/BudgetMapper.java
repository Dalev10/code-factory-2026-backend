package com.code_factory.backend.budgeting.infrastructure.adapter.out.persistence.mapper;

import com.code_factory.backend.budgeting.domain.model.Budget;
import com.code_factory.backend.budgeting.infrastructure.adapter.out.persistence.entity.BudgetEntity;
import org.springframework.stereotype.Component;

@Component
public class BudgetMapper {

    public BudgetEntity toEntity(Budget domain) {
        return BudgetEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .month(domain.getMonth())
                .totalIncome(domain.getTotalIncome())
                .expenseLimit(domain.getExpenseLimit())
                .build();
    }

    public Budget toDomain(BudgetEntity entity) {
        return Budget.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .month(entity.getMonth())
                .totalIncome(entity.getTotalIncome())
                .expenseLimit(entity.getExpenseLimit())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
