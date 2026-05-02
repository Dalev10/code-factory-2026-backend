package com.code_factory.backend.budgeting.application.port.out;

import com.code_factory.backend.budgeting.domain.model.Budget;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BudgetRepositoryPort {
    Budget save(Budget budget);
    List<Budget> findByUserId(UUID userId);
    Optional<Budget> findByUserIdAndMonth(UUID userId, LocalDate month);

    void delete(Budget budget); // 👈 ESTE ES NUEVO
}
