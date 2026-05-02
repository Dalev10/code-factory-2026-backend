package com.code_factory.backend.budgeting.domain.model;

import java.util.List;

public record BudgetProgressReport(
        BudgetSummary globalSummary,         
        List<CategoryProgress> categoryDetails // El desglose por categoría
) {}