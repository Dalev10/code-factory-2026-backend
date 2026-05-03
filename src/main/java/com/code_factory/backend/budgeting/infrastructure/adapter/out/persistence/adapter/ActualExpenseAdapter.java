package com.code_factory.backend.budgeting.infrastructure.adapter.out.persistence.adapter;

import com.code_factory.backend.budgeting.application.port.out.ActualExpensePort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public Map<UUID, BigDecimal> getExpensesByCategory(UUID userId, LocalDate month) {
        LocalDate startDate = month.withDayOfMonth(1);
        LocalDate endDate = month.withDayOfMonth(month.lengthOfMonth());

        String sql = """
            SELECT t.category_id, COALESCE(SUM(t.amount), 0) 
            FROM transactions t
            INNER JOIN categories c ON t.category_id = c.id
            WHERE t.user_id = :userId 
              AND c.type = 'EXPENSE'
              AND t.transaction_date >= :startDate 
              AND t.transaction_date <= :endDate
            GROUP BY t.category_id
            """;

        @SuppressWarnings("unchecked")
        List<Object[]> results = entityManager.createNativeQuery(sql)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();

        Map<UUID, BigDecimal> expensesByCategory = new HashMap<>();
        for (Object[] row : results) {
            UUID categoryId = (UUID) row[0];
            BigDecimal amount = new BigDecimal(row[1].toString());
            expensesByCategory.put(categoryId, amount);
        }

        return expensesByCategory;
    }
}