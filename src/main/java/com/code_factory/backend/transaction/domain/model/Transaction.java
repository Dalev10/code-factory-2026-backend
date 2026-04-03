package com.code_factory.backend.transaction.domain.model;

import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
public class Transaction {
    private final UUID id;
    private final UUID userId;
    private final UUID categoryId;
    private final BigDecimal amount;
    private final String description;
    private final LocalDate transactionDate;
}