// /src/main/java/com/code_factory/backend/budgeting/application/port/out/BudgetAllocationRepositoryPort.java
package com.code_factory.backend.budgeting.application.port.out;

import com.code_factory.backend.budgeting.domain.model.BudgetAllocation;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BudgetAllocationRepositoryPort {
    BudgetAllocation save(BudgetAllocation allocation);
    List<BudgetAllocation> findByBudgetId(UUID budgetId);
    Optional<BudgetAllocation> findByBudgetIdAndCategoryId(UUID budgetId, UUID categoryId);
    BigDecimal sumAmountByBudgetId(UUID budgetId);
}