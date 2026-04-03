package com.code_factory.backend.transaction.application.service;

import com.code_factory.backend.transaction.application.port.in.ListTransactionsUseCase;
import com.code_factory.backend.transaction.application.port.out.TransactionRepositoryPort;
import com.code_factory.backend.transaction.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListTransactionsService implements ListTransactionsUseCase {
    private final TransactionRepositoryPort transactionRepositoryPort;

    @Override
    public List<Transaction> getHistoryByUserId(UUID userId, int limit, int offset) {
        return transactionRepositoryPort.findByUserId(userId, limit, offset);
    }
}