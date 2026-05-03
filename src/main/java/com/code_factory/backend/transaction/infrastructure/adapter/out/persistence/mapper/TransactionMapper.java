package com.code_factory.backend.transaction.infrastructure.adapter.out.persistence.mapper;

import com.code_factory.backend.transaction.domain.model.Transaction;
import com.code_factory.backend.transaction.infrastructure.adapter.out.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public TransactionEntity toEntity(Transaction domain) {
        return TransactionEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .categoryId(domain.getCategoryId())
                .amount(domain.getAmount())
                .description(domain.getDescription())
                .transactionDate(domain.getTransactionDate())
                .build();
    }

    public Transaction toDomain(TransactionEntity entity) {
        return Transaction.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .categoryId(entity.getCategoryId())
                .amount(entity.getAmount())
                .description(entity.getDescription())
                .transactionDate(entity.getTransactionDate())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}