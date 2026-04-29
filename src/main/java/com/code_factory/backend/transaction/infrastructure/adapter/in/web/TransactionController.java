package com.code_factory.backend.transaction.infrastructure.adapter.in.web;

import com.code_factory.backend.classification.application.port.out.CategoryRepositoryPort;
import com.code_factory.backend.classification.domain.model.CategoryType;
import com.code_factory.backend.transaction.application.port.in.DeleteTransactionCommand;
import com.code_factory.backend.transaction.application.port.in.DeleteTransactionUseCase;
import com.code_factory.backend.transaction.application.port.in.ListTransactionsUseCase;
import com.code_factory.backend.transaction.application.port.in.RegisterIncomeCommand;
import com.code_factory.backend.transaction.application.port.in.RegisterIncomeUseCase;
import com.code_factory.backend.transaction.application.port.in.RegisterExpenseCommand;
import com.code_factory.backend.transaction.application.port.in.RegisterExpenseUseCase;
import com.code_factory.backend.transaction.domain.model.Transaction;
import com.code_factory.backend.transaction.infrastructure.adapter.in.web.dto.RegisterIncomeRequest;
import com.code_factory.backend.transaction.infrastructure.adapter.in.web.dto.RegisterExpenseRequest;
import com.code_factory.backend.transaction.infrastructure.adapter.in.web.dto.TransactionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final RegisterIncomeUseCase registerIncomeUseCase;
    private final RegisterExpenseUseCase registerExpenseUseCase;
    private final ListTransactionsUseCase listTransactionsUseCase;
    private final DeleteTransactionUseCase deleteTransactionUseCase;
    private final CategoryRepositoryPort categoryRepositoryPort;

    @PostMapping("/income")
    public ResponseEntity<Transaction> registerIncome(@Valid @RequestBody RegisterIncomeRequest request) {
        var command = new RegisterIncomeCommand(
                request.getUserId(),
                request.getCategoryId(),
                request.getAmount(),
                request.getDescription(),
                request.getTransactionDate()
        );
        // CORREGIDO: Usando el nombre de variable correcto
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registerIncomeUseCase.registerIncome(command));
    }

    @PostMapping("/expense")
    public ResponseEntity<Transaction> registerExpense(@Valid @RequestBody RegisterExpenseRequest request) {
        var command = new RegisterExpenseCommand(
                request.getUserId(),
                request.getCategoryId(),
                request.getAmount(),
                request.getDescription(),
                request.getTransactionDate()
        );
        // CORREGIDO: Usando el nombre de variable correcto
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registerExpenseUseCase.registerExpense(command));
    }

@GetMapping("/history/{userId}")
    public ResponseEntity<List<TransactionResponse>> getHistory(
            @PathVariable UUID userId,
            @RequestParam(required = false) CategoryType type, // El filtro mágico
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {

        List<TransactionResponse> history = listTransactionsUseCase.getHistoryByUserId(userId, type, limit, offset)
                .stream()
                .map(t -> {
                    var category = categoryRepositoryPort.findById(t.getCategoryId()).orElse(null);
                    return TransactionResponse.builder()
                            .id(t.getId())
                            .amount(t.getAmount())
                            .description(t.getDescription())
                            .transactionDate(t.getTransactionDate())
                            .categoryId(t.getCategoryId())
                            .categoryName(category != null ? category.getName() : "Desconocida")
                            .categoryType(category != null ? category.getType() : null)
                            .build();
                })
                .toList();

        return ResponseEntity.ok(history);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable UUID transactionId,
            @RequestParam UUID userId) {
        deleteTransactionUseCase.deleteTransaction(new DeleteTransactionCommand(transactionId, userId));
        return ResponseEntity.noContent().build();
    }
}