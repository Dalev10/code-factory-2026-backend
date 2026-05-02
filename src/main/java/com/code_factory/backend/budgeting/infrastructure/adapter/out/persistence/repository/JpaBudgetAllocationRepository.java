// /src/main/java/com/code_factory/backend/budgeting/infrastructure/adapter/out/persistence/repository/JpaBudgetAllocationRepository.java
package com.code_factory.backend.budgeting.infrastructure.adapter.out.persistence.repository;

import com.code_factory.backend.budgeting.infrastructure.adapter.out.persistence.entity.BudgetAllocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaBudgetAllocationRepository extends JpaRepository<BudgetAllocationEntity, UUID> {
    
    List<BudgetAllocationEntity> findByBudgetId(UUID budgetId);
    
    Optional<BudgetAllocationEntity> findByBudgetIdAndCategoryId(UUID budgetId, UUID categoryId);

    // Suma nativa delegada a BD para rendimiento y prevención de Nulls
    @Query("SELECT COALESCE(SUM(b.amount), 0) FROM BudgetAllocationEntity b WHERE b.budgetId = :budgetId")
    BigDecimal sumAmountByBudgetId(@Param("budgetId") UUID budgetId);
}