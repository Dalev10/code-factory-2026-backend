package com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class CategoryProgressResponse {
    private UUID categoryId;
    private String categoryName;
    private BigDecimal allocatedAmount;
    private BigDecimal spentAmount;
    private BigDecimal remainingAmount;
    private double progressPercentage;
}