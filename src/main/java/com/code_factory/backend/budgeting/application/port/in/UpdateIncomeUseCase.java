package com.code_factory.backend.budgeting.application.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public interface UpdateIncomeUseCase {
    void update(UUID userId, BigDecimal newIncome);
}