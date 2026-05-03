package com.code_factory.backend.transaction.application.service;

import com.code_factory.backend.transaction.application.port.in.DeleteTransactionCommand;
import com.code_factory.backend.transaction.application.port.in.DeleteTransactionUseCase;
import com.code_factory.backend.transaction.application.port.out.TransactionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeleteTransactionService implements DeleteTransactionUseCase {

    private final TransactionRepositoryPort transactionRepositoryPort;

    @Override
    public void deleteTransaction(DeleteTransactionCommand command) {
        var transaction = transactionRepositoryPort.findById(command.transactionId())
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        if (!transaction.getUserId().equals(command.userId())) {
            throw new RuntimeException("No tienes permiso para eliminar esta transacción");
        }

        if (transaction.getCreatedAt() == null || transaction.getCreatedAt().isBefore(LocalDateTime.now().minusHours(48))) {
            throw new RuntimeException("La transacción solo puede eliminarse dentro de las 48 horas siguientes a su creación");
        }

        transactionRepositoryPort.delete(command.transactionId());
    }
}
