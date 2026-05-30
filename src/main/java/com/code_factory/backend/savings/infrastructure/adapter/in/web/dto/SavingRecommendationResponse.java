package com.code_factory.backend.savings.infrastructure.adapter.in.web.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class SavingRecommendationResponse {
    private UUID categoryId;
    private String categoryName;
    private BigDecimal allocatedAmount;
    private BigDecimal spentAmount;
    private double progressPercentage;
    private String message;
}
