package com.code_factory.backend.budgeting.application.port.out;

import com.code_factory.backend.budgeting.domain.model.Budget;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BudgetRepositoryPort {

    /**
     * Guarda o actualiza un presupuesto
     */
    Budget save(Budget budget);

    /**
     * Obtiene todos los presupuestos de un usuario
     */
    List<Budget> findByUserId(UUID userId);

    /**
     * Obtiene el presupuesto de un usuario en un mes específico
     */
    Optional<Budget> findByUserIdAndMonth(UUID userId, LocalDate month);

    Optional<Budget> findById(UUID id);
}
