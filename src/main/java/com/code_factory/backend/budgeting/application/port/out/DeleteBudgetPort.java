package com.code_factory.backend.budgeting.application.port.out;

public interface DeleteBudgetPort {
    boolean existsByUserId(Long userId);
    void deleteById(Long id);
}
