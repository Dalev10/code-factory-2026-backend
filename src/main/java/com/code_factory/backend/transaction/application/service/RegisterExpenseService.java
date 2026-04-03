package com.code_factory.backend.transaction.application.service;

import com.code_factory.backend.classification.application.port.out.CategoryRepositoryPort;
import com.code_factory.backend.classification.domain.model.CategoryType;
import com.code_factory.backend.identity.application.port.out.UserRepositoryPort;
import com.code_factory.backend.transaction.application.port.in.RegisterExpenseCommand;
import com.code_factory.backend.transaction.application.port.in.RegisterExpenseUseCase;
import com.code_factory.backend.transaction.application.port.out.TransactionRepositoryPort;
import com.code_factory.backend.transaction.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterExpenseService implements RegisterExpenseUseCase {

    private final TransactionRepositoryPort transactionRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public Transaction registerExpense(RegisterExpenseCommand command) {
        // 1. Validar Usuario
        userRepositoryPort.findById(command.userId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Validar Categoría y Tipo (Debe ser GASTO)
        var category = categoryRepositoryPort.findById(command.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        if (category.getType() != CategoryType.EXPENSE) {
            throw new RuntimeException("La categoría seleccionada debe ser de tipo GASTO");
        }

        // 3. Crear Objeto de Dominio
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
}