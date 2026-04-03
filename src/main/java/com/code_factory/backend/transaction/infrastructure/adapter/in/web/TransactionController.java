package com.code_factory.backend.transaction.infrastructure.adapter.in.web;

import com.code_factory.backend.transaction.application.port.in.RegisterIncomeCommand;
import com.code_factory.backend.transaction.application.port.in.RegisterTransactionUseCase;
import com.code_factory.backend.transaction.domain.model.Transaction;
import com.code_factory.backend.transaction.infrastructure.adapter.in.web.dto.RegisterIncomeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final RegisterTransactionUseCase registerTransactionUseCase;

    @PostMapping("/income")
    public ResponseEntity<Transaction> registerIncome(@Valid @RequestBody RegisterIncomeRequest request) {
        var command = new RegisterIncomeCommand(
                request.getUserId(),
                request.getCategoryId(),
                request.getAmount(),
                request.getDescription(),
                request.getTransactionDate()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registerTransactionUseCase.registerIncome(command));
    }
}