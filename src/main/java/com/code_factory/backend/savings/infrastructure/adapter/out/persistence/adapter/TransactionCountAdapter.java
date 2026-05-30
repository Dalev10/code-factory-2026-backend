package com.code_factory.backend.savings.infrastructure.adapter.out.persistence.adapter;

import com.code_factory.backend.savings.application.port.out.TransactionCountPort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionCountAdapter implements TransactionCountPort {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public long countByUserId(UUID userId) {
        String sql = "SELECT COUNT(*) FROM transactions WHERE user_id = :userId";
        Object result = entityManager.createNativeQuery(sql)
                .setParameter("userId", userId)
                .getSingleResult();
        return ((Number) result).longValue();
    }
}
