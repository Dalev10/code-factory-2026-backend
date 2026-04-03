package com.code_factory.backend.transaction.infrastructure.adapter.out.persistence.adapter;

import com.code_factory.backend.transaction.application.port.out.TransactionRepositoryPort;
import com.code_factory.backend.transaction.domain.model.Transaction;
import com.code_factory.backend.transaction.infrastructure.adapter.out.persistence.entity.TransactionEntity;
import com.code_factory.backend.transaction.infrastructure.adapter.out.persistence.mapper.TransactionMapper;
import com.code_factory.backend.transaction.infrastructure.adapter.out.persistence.repository.JpaTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
        TransactionEntity entity = mapper.toEntity(transaction);
        // Seteamos la auditoría que el dominio no conoce
        entity.setCreatedAt(LocalDateTime.now());
        TransactionEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Transaction> findByUserId(UUID userId, int limit, int offset) {
        // Traducimos el estándar del puerto (limit/offset) al estándar de Spring (Pageable)
        Pageable pageable = PageRequest.of(offset / limit, limit);
        
        return repository.findByUserIdOrderByTransactionDateDesc(userId, pageable)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}