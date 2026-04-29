package com.code_factory.backend.transaction.application.port.in;

public interface DeleteTransactionUseCase {
    void deleteTransaction(DeleteTransactionCommand command);
}
