package com.code_factory.backend.transaction.infrastructure.adapter.in.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.code_factory.backend.classification.domain.model.CategoryType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionResponse {
    private UUID id;
    private BigDecimal amount;
    private String description;
    private LocalDate transactionDate;
    private UUID categoryId;
    private String categoryName;
    private CategoryType categoryType;
}