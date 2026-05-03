package com.code_factory.backend.budgeting.infrastructure.adapter.in.web;

import com.code_factory.backend.budgeting.application.port.in.CreateBudgetCommand;
import com.code_factory.backend.budgeting.application.port.in.CreateBudgetUseCase;
import com.code_factory.backend.budgeting.application.port.in.DeleteBudgetCommand;
import com.code_factory.backend.budgeting.application.port.in.DeleteBudgetUseCase;
import com.code_factory.backend.budgeting.application.port.in.GetBudgetProgressUseCase;
import com.code_factory.backend.budgeting.application.port.in.GetBudgetSummaryUseCase;
import com.code_factory.backend.budgeting.application.port.in.ListBudgetsUseCase;
import com.code_factory.backend.budgeting.application.port.in.UpdateBudgetUseCase;
import com.code_factory.backend.budgeting.application.port.in.UpdateBudgetCommand;
import com.code_factory.backend.budgeting.domain.model.Budget;
import com.code_factory.backend.budgeting.domain.model.BudgetSummary;
import com.code_factory.backend.budgeting.domain.model.BudgetProgressReport;
import com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto.BudgetProgressResponse;
import com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto.BudgetResponse;
import com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto.BudgetSummaryResponse;
import com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto.CategoryProgressResponse;
import com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto.CreateBudgetRequest;
import com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto.UpdateBudgetRequest;



import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final CreateBudgetUseCase createBudgetUseCase;
    private final ListBudgetsUseCase listBudgetsUseCase;
    private final UpdateBudgetUseCase updateBudgetUseCase; 
    private final GetBudgetSummaryUseCase getBudgetSummaryUseCase;
    private final GetBudgetProgressUseCase getBudgetProgressUseCase;
    private final DeleteBudgetUseCase deleteBudgetUseCase;

    @PostMapping
    public ResponseEntity<BudgetResponse> createBudget(@Valid @RequestBody CreateBudgetRequest request) {

        CreateBudgetCommand command = new CreateBudgetCommand(
                request.getUserId(),
                request.getMonth(),
                request.getTotalIncome(),
                request.getExpenseLimit()
        );

        Budget budget = createBudgetUseCase.createBudget(command);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BudgetResponse.builder()
                        .id(budget.getId())
                        .userId(budget.getUserId())
                        .month(budget.getMonth())
                        .totalIncome(budget.getTotalIncome())
                        .expenseLimit(budget.getExpenseLimit())
                        .createdAt(budget.getCreatedAt())
                        .build());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<BudgetResponse>> getBudgetsByUser(@PathVariable UUID userId) {

        List<BudgetResponse> budgets = listBudgetsUseCase.getBudgetsByUserId(userId)
                .stream()
                .map(b -> BudgetResponse.builder()
                        .id(b.getId())
                        .userId(b.getUserId())
                        .month(b.getMonth())
                        .totalIncome(b.getTotalIncome())
                        .expenseLimit(b.getExpenseLimit())
                        .createdAt(b.getCreatedAt())
                        .build())
                .toList();

        return ResponseEntity.ok(budgets);
    }

  
    @PutMapping("/{budgetId}")
    public ResponseEntity<BudgetResponse> updateBudget(
            @PathVariable UUID budgetId,
            @RequestParam UUID userId, 
            @Valid @RequestBody UpdateBudgetRequest request) { 

        UpdateBudgetCommand command = new UpdateBudgetCommand(
                userId,
                budgetId,
                request.getNewTotalIncome(),
                request.getNewLimit()
        );

        Budget updatedBudget = updateBudgetUseCase.updateBudget(command);

        BudgetResponse response = BudgetResponse.builder()
                .id(updatedBudget.getId())
                .userId(updatedBudget.getUserId())
                .month(updatedBudget.getMonth())
                .totalIncome(updatedBudget.getTotalIncome())
                .expenseLimit(updatedBudget.getExpenseLimit())
                .createdAt(updatedBudget.getCreatedAt())
                .build();
        
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{userId}/summary")
    public ResponseEntity<BudgetSummaryResponse> getBudgetSummary(
            @PathVariable UUID userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate month) {

        BudgetSummary summary = getBudgetSummaryUseCase.getSummary(userId, month);

        BudgetSummaryResponse response = BudgetSummaryResponse.builder()
                .budgetId(summary.budgetId())
                .totalIncome(summary.totalIncome())
                .expenseLimit(summary.expenseLimit())
                .totalSpent(summary.totalSpent())
                .remainingBalance(summary.remainingBalance())
                .isExceeded(summary.isExceeded())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/progress")
    public ResponseEntity<BudgetProgressResponse> getBudgetProgress(
            @PathVariable UUID userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate month) {

        // 1. Ejecutar el caso de uso
        BudgetProgressReport progressReport = getBudgetProgressUseCase.getProgress(userId, month);

        // 2. Mapear el resumen global
        BudgetSummaryResponse globalSummaryResponse = BudgetSummaryResponse.builder()
                .budgetId(progressReport.globalSummary().budgetId())
                .totalIncome(progressReport.globalSummary().totalIncome())
                .expenseLimit(progressReport.globalSummary().expenseLimit())
                .totalSpent(progressReport.globalSummary().totalSpent())
                .remainingBalance(progressReport.globalSummary().remainingBalance())
                .isExceeded(progressReport.globalSummary().isExceeded())
                .build();

        // 3. Mapear el detalle por categorías
        List<CategoryProgressResponse> categoryDetailsResponse = progressReport.categoryDetails().stream()
                .map(detail -> CategoryProgressResponse.builder()
                        .categoryId(detail.categoryId())
                        .categoryName(detail.categoryName())
                        .allocatedAmount(detail.allocatedAmount())
                        .spentAmount(detail.spentAmount())
                        .remainingAmount(detail.remainingAmount())
                        .progressPercentage(detail.progressPercentage())
                        .build())
                .toList();

        // 4. Construir la respuesta final unificada
        BudgetProgressResponse response = BudgetProgressResponse.builder()
                .globalSummary(globalSummaryResponse)
                .categoryDetails(categoryDetailsResponse)
                .build();

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBudget(
        @PathVariable UUID id, 
        @RequestParam UUID userId) {

        deleteBudgetUseCase.deleteBudget(new DeleteBudgetCommand(id, userId));
        return ResponseEntity
                .ok("Presupuesto eliminado correctamente");
    }

}