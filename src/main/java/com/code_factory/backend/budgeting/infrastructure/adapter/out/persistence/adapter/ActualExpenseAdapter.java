package com.code_factory.backend.budgeting.infrastructure.adapter.out.persistence.adapter;

import com.code_factory.backend.budgeting.application.port.out.ActualExpensePort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ActualExpenseAdapter implements ActualExpensePort {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public BigDecimal sumActualExpensesByUserAndMonth(UUID userId, LocalDate month) {
        // Obtenemos el primer y último día del mes para el filtro de fechas
        LocalDate startDate = month.withDayOfMonth(1);
        LocalDate endDate = month.withDayOfMonth(month.lengthOfMonth());

        // Consulta nativa optimizada cruzando transacciones con categorías tipo EXPENSE
        String sql = """
            SELECT COALESCE(SUM(t.amount), 0) 
            FROM transactions t
            INNER JOIN categories c ON t.category_id = c.id
            WHERE t.user_id = :userId 
              AND c.type = 'EXPENSE'
              AND t.transaction_date >= :startDate 
              AND t.transaction_date <= :endDate
            """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();

        return new BigDecimal(result.toString());
    }
}