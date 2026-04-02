package com.code_factory.backend.transaction.application.port.out;

import com.code_factory.backend.transaction.domain.model.Transaction;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepositoryPort {
    
    // Para la HU 2.1.2: Registrar Ingreso
    Transaction save(Transaction transaction);

    // Para futuras consultas (Paginación agnóstica)
    // Usamos tipos simples de Java
    List<Transaction> findByUserId(UUID userId, int limit, int offset);
    
    Optional<Transaction> findById(UUID id);
}