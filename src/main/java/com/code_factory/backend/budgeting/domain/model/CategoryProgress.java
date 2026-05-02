package com.code_factory.backend.budgeting.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record CategoryProgress(
        UUID categoryId,
        String categoryName,
        BigDecimal allocatedAmount, // Lo planeado 
        BigDecimal spentAmount,     // Lo gastado real
        BigDecimal remainingAmount, // Lo que le sobra en esta categoría
        double progressPercentage   // % de consumo
) {}