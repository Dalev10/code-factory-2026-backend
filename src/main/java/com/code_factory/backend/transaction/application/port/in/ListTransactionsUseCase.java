package com.code_factory.backend.transaction.application.port.in;

import com.code_factory.backend.classification.domain.model.CategoryType;
import com.code_factory.backend.transaction.domain.model.Transaction;
import java.util.List;
import java.util.UUID;

public interface ListTransactionsUseCase {
    List<Transaction> getHistoryByUserId(UUID userId, CategoryType type, int limit, int offset);
}