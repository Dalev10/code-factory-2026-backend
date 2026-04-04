package com.code_factory.backend.transaction.infrastructure.adapter.out.persistence.repository;

import com.code_factory.backend.classification.domain.model.CategoryType;
import com.code_factory.backend.transaction.infrastructure.adapter.out.persistence.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.UUID;

public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    
    @Query("SELECT t FROM TransactionEntity t " +
           "JOIN CategoryEntity c ON t.categoryId = c.id " +
           "WHERE t.userId = :userId " +
           "AND (:type IS NULL OR c.type = :type) " +
           "ORDER BY t.transactionDate DESC")
    Page<TransactionEntity> findByUserIdAndType(
            @Param("userId") UUID userId, 
            @Param("type") CategoryType type, 
            Pageable pageable
    );
}