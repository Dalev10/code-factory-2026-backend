// /src/main/java/com/code_factory/backend/budgeting/infrastructure/adapter/out/persistence/adapter/BudgetAllocationPersistenceAdapter.java
package com.code_factory.backend.budgeting.infrastructure.adapter.out.persistence.adapter;

import com.code_factory.backend.budgeting.application.port.out.BudgetAllocationRepositoryPort;
import com.code_factory.backend.budgeting.domain.model.BudgetAllocation;
import com.code_factory.backend.budgeting.infrastructure.adapter.out.persistence.mapper.BudgetAllocationMapper;
import com.code_factory.backend.budgeting.infrastructure.adapter.out.persistence.repository.JpaBudgetAllocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BudgetAllocationPersistenceAdapter implements BudgetAllocationRepositoryPort {

    private final JpaBudgetAllocationRepository repository;
    private final BudgetAllocationMapper mapper;

    @Override
    public BudgetAllocation save(BudgetAllocation allocation) {
        var entity = repository.save(mapper.toEntity(allocation));
        return mapper.toDomain(entity);
    }

    @Override
    public List<BudgetAllocation> findByBudgetId(UUID budgetId) {
        return repository.findByBudgetId(budgetId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BudgetAllocation> findByBudgetIdAndCategoryId(UUID budgetId, UUID categoryId) {
        return repository.findByBudgetIdAndCategoryId(budgetId, categoryId)
                .map(mapper::toDomain);
    }

    @Override
    public BigDecimal sumAmountByBudgetId(UUID budgetId) {
        return repository.sumAmountByBudgetId(budgetId);
    }
}