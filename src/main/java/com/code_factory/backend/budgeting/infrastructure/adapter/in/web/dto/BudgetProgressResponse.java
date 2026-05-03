package com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BudgetProgressResponse {
    private BudgetSummaryResponse globalSummary; // Reutilizamos el DTO de la HU 5.2.2
    private List<CategoryProgressResponse> categoryDetails;
}