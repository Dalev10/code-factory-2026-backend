package com.code_factory.backend.savings.infrastructure.adapter.in.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SavingsReportResponse {
    private boolean hasEnoughData;
    private String message;
    private List<SavingRecommendationResponse> recommendations;
}
