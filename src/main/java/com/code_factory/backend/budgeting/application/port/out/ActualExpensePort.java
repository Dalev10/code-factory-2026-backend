package com.code_factory.backend.budgeting.application.port.out;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public interface ActualExpensePort {
    /**
     * Calcula la suma de todos los gastos reales de un usuario en un mes específico.
     */
    BigDecimal sumActualExpensesByUserAndMonth(UUID userId, LocalDate month);
    Map<UUID, BigDecimal> getExpensesByCategory(UUID userId, LocalDate month);
}