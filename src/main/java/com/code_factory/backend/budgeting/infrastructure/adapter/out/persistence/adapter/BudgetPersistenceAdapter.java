package com.code_factory.backend.budgeting.infrastructure.adapter.out.persistence.adapter;

import com.code_factory.backend.budgeting.application.port.out.BudgetRepositoryPort;
import com.code_factory.backend.budgeting.domain.model.Budget;
import com.code_factory.backend.budgeting.infrastructure.adapter.out.persistence.mapper.BudgetMapper;
import com.code_factory.backend.budgeting.infrastructure.adapter.out.persistence.repository.JpaBudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BudgetPersistenceAdapter implements BudgetRepositoryPort {

    private final JpaBudgetRepository repository;
    private final BudgetMapper mapper;

    @Override
    public Budget save(Budget budget) {
        return mapper.toDomain(repository.save(mapper.toEntity(budget)));
    }

    @Override
    public List<Budget> findByUserId(UUID userId) {
        return repository.findByUserId(userId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Budget> findByUserIdAndMonth(UUID userId, LocalDate month) {
        return repository.findByUserIdAndMonth(userId, month)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Budget> findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }



    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}