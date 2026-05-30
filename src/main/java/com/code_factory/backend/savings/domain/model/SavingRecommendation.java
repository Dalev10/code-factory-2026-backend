package com.code_factory.backend.savings.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record SavingRecommendation(
        UUID categoryId,
        String categoryName,
        BigDecimal allocatedAmount,
        BigDecimal spentAmount,
        double progressPercentage,
        String message
) {}
