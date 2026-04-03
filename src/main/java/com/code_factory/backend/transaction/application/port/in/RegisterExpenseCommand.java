package com.code_factory.backend.transaction.application.port.in;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class RegisterExpenseCommand {
    private UUID userId;
    private UUID categoryId;
    private BigDecimal amount;
    private String description;
    private LocalDate transactionDate;

    public RegisterExpenseCommand(UUID userId, UUID categoryId, BigDecimal amount, String description, LocalDate transactionDate) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    public UUID getUserId() { return userId; }
    public UUID getCategoryId() { return categoryId; }
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDate getTransactionDate() { return transactionDate; }
    
}
