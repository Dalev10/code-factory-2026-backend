package com.code_factory.backend.transaction.infrastructure.adapter.out.persistence.repository;

import com.code_factory.backend.transaction.infrastructure.adapter.out.persistence.entity.TransactionEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    // En JpaTransactionRepository.java
    Page<TransactionEntity> findByUserIdOrderByTransactionDateDesc(UUID userId, Pageable pageable);
}