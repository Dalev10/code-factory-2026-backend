package com.code_factory.backend.budgeting.infrastructure.adapter.out.persistence.repository;

import com.code_factory.backend.budgeting.infrastructure.adapter.out.persistence.entity.BudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaBudgetRepository extends JpaRepository<BudgetEntity, UUID> {

    List<BudgetEntity> findByUserId(UUID userId);

    Optional<BudgetEntity> findByUserIdAndMonth(UUID userId, LocalDate month);


    void deleteById(UUID id);
}