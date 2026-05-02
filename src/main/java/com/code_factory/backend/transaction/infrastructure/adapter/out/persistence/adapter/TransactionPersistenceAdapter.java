package com.code_factory.backend.transaction.infrastructure.adapter.out.persistence.adapter;

import com.code_factory.backend.classification.domain.model.CategoryType;
import com.code_factory.backend.transaction.application.port.out.TransactionRepositoryPort;
import com.code_factory.backend.transaction.domain.model.Transaction;
import com.code_factory.backend.transaction.infrastructure.adapter.out.persistence.mapper.TransactionMapper;
import com.code_factory.backend.transaction.infrastructure.adapter.out.persistence.repository.JpaTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionPersistenceAdapter implements TransactionRepositoryPort {
    private final JpaTransactionRepository repository;
    private final TransactionMapper mapper;

    @Override
    public Transaction save(Transaction transaction) {
        return mapper.toDomain(repository.save(mapper.toEntity(transaction)));
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Transaction> findByUserIdAndType(UUID userId, CategoryType type, int limit, int offset) {
        var pageable = PageRequest.of(offset / limit, limit);
        return repository.findByUserIdAndType(userId, type, pageable)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}