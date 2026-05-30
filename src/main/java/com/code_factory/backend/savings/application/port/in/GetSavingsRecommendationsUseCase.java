package com.code_factory.backend.savings.application.port.in;

import com.code_factory.backend.savings.domain.model.SavingsReport;

import java.time.LocalDate;
import java.util.UUID;

public interface GetSavingsRecommendationsUseCase {
    SavingsReport getRecommendations(UUID userId, LocalDate month);
}
