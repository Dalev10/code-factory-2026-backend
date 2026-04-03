package com.code_factory.backend.transaction.application.service;

import com.code_factory.backend.classification.application.port.out.CategoryRepositoryPort;
import com.code_factory.backend.classification.domain.model.CategoryType;
import com.code_factory.backend.identity.application.port.out.UserRepositoryPort;
import com.code_factory.backend.transaction.application.port.in.RegisterExpenseCommand;
import com.code_factory.backend.transaction.application.port.in.RegisterIncomeCommand;
import com.code_factory.backend.transaction.application.port.in.RegisterTransactionUseCase;
import com.code_factory.backend.transaction.application.port.out.TransactionRepositoryPort;
import com.code_factory.backend.transaction.application.port.in.RegisterExpenseCommand;
import com.code_factory.backend.transaction.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterTransactionService implements RegisterTransactionUseCase {

    private final TransactionRepositoryPort transactionRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public Transaction registerIncome(RegisterIncomeCommand command) {
        // 1. Validar existencia del usuario
        userRepositoryPort.findById(command.userId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Validar existencia y TIPO de categoría (HU 2.1.2)
        var category = categoryRepositoryPort.findById(command.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        if (category.getType() != CategoryType.INCOME) {
            throw new RuntimeException("La categoría debe ser de tipo INGRESO");
        }

        // 3. Mapear Command a Dominio (Asignando ID de negocio)
        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .userId(command.userId())
                .categoryId(command.categoryId())
                .amount(command.amount())
                .description(command.description())
                .transactionDate(command.transactionDate())
                .build();

        return transactionRepositoryPort.save(transaction);
    }

    @Override
    public Transaction registerExpense(RegisterExpenseCommand command) {

        // 1. Validar usuario
        userRepositoryPort.findById(command.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Validar categoría
        var category = categoryRepositoryPort.findById(command.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        //  CAMBIO CLAVE
        if (category.getType() != CategoryType.EXPENSE) {
            throw new RuntimeException("La categoría debe ser de tipo GASTO");
        }

        // 3. Crear transacción
        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .userId(command.getUserId())
                .categoryId(command.getCategoryId())
                .amount(command.getAmount())
                .description(command.getDescription())
                .transactionDate(command.getTransactionDate())
                .build();

        return transactionRepositoryPort.save(transaction);
}
}