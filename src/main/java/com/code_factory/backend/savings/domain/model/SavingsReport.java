package com.code_factory.backend.savings.domain.model;

import java.util.List;

public record SavingsReport(
        boolean hasEnoughData,
        String message,
        List<SavingRecommendation> recommendations
) {}
