package com.code_factory.backend.savings.infrastructure.adapter.in.web;

import com.code_factory.backend.savings.application.port.in.GetSavingsRecommendationsUseCase;
import com.code_factory.backend.savings.domain.model.SavingsReport;
import com.code_factory.backend.savings.infrastructure.adapter.in.web.dto.SavingRecommendationResponse;
import com.code_factory.backend.savings.infrastructure.adapter.in.web.dto.SavingsReportResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/savings-recommendations")
@RequiredArgsConstructor
public class SavingsRecommendationsController {

    private final GetSavingsRecommendationsUseCase getSavingsRecommendationsUseCase;

    @GetMapping("/{userId}")
    public ResponseEntity<SavingsReportResponse> getRecommendations(
            @PathVariable UUID userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate month) {

        LocalDate targetMonth = (month != null) ? month : LocalDate.now();

        SavingsReport report = getSavingsRecommendationsUseCase.getRecommendations(userId, targetMonth);

        List<SavingRecommendationResponse> recommendationResponses = report.recommendations().stream()
                .map(rec -> SavingRecommendationResponse.builder()
                        .categoryId(rec.categoryId())
                        .categoryName(rec.categoryName())
                        .allocatedAmount(rec.allocatedAmount())
                        .spentAmount(rec.spentAmount())
                        .progressPercentage(rec.progressPercentage())
                        .message(rec.message())
                        .build())
                .toList();

        SavingsReportResponse response = SavingsReportResponse.builder()
                .hasEnoughData(report.hasEnoughData())
                .message(report.message())
                .recommendations(recommendationResponses)
                .build();

        return ResponseEntity.ok(response);
    }
}
