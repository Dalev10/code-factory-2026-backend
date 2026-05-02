package com.code_factory.backend.budgeting.application.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public interface UpdateBudgetUseCase {
    void updateExpenseLimit(UUID userId, BigDecimal newLimit);
}
