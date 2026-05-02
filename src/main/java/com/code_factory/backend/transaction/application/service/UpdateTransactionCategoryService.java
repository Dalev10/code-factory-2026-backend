package com.code_factory.backend.transaction.application.service;

import com.code_factory.backend.classification.application.port.out.CategoryRepositoryPort;
import com.code_factory.backend.transaction.application.port.in.UpdateTransactionCategoryCommand;
import com.code_factory.backend.transaction.application.port.in.UpdateTransactionCategoryUseCase;
import com.code_factory.backend.transaction.application.port.out.TransactionRepositoryPort;
import com.code_factory.backend.transaction.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateTransactionCategoryService implements UpdateTransactionCategoryUseCase {

    private final TransactionRepositoryPort transactionRepositoryPort;
    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public Transaction execute(UpdateTransactionCategoryCommand command) {

        // 1. Buscar transacción
        Transaction currentTransaction = transactionRepositoryPort.findById(command.transactionId())
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        // 2. Validar categoría
        categoryRepositoryPort.findById(command.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // 3. Crear nueva transacción con la categoría actualizada
        Transaction updatedTransaction = Transaction.builder()
                .id(currentTransaction.getId())
                .userId(currentTransaction.getUserId())
                .categoryId(command.categoryId()) // solo cambia esto
                .amount(currentTransaction.getAmount())
                .description(currentTransaction.getDescription())
                .transactionDate(currentTransaction.getTransactionDate())
                .build();

        // 4. Guardar
        return transactionRepositoryPort.save(updatedTransaction);
    }
}