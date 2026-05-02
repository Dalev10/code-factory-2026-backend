package com.code_factory.backend.budgeting.infrastructure.adapter.in.web;

import com.code_factory.backend.budgeting.application.port.in.CreateBudgetCommand;
import com.code_factory.backend.budgeting.application.port.in.CreateBudgetUseCase;
import com.code_factory.backend.budgeting.application.port.in.GetBudgetSummaryUseCase;
import com.code_factory.backend.budgeting.application.port.in.ListBudgetsUseCase;
import com.code_factory.backend.budgeting.application.port.in.UpdateBudgetUseCase;
import com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto.BudgetResponse;
import com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto.BudgetSummaryResponse;
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

    @PostMapping
    public ResponseEntity<BudgetResponse> createBudget(@Valid @RequestBody CreateBudgetRequest request) {

        var command = new CreateBudgetCommand(
                request.getUserId(),
                request.getMonth(),
                request.getTotalIncome(),
                request.getExpenseLimit()
        );

        var budget = createBudgetUseCase.createBudget(command);

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

    //  NUEVO MÉTODO (editar presupuesto)
    @PutMapping("/monthly")
    public ResponseEntity<?> updateBudget(@RequestBody @Valid UpdateBudgetRequest request) {

        try {
            updateBudgetUseCase.updateExpenseLimit(
                    request.getUserId(),
                    request.getNewLimit()
            );

            return ResponseEntity.ok("Presupuesto actualizado correctamente");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo completar la acción");
        }
    }


    @GetMapping("/{userId}/summary")
    public ResponseEntity<BudgetSummaryResponse> getBudgetSummary(
            @PathVariable UUID userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate month) {

        var summary = getBudgetSummaryUseCase.getSummary(userId, month);

        var response = BudgetSummaryResponse.builder()
                .budgetId(summary.budgetId())
                .totalIncome(summary.totalIncome())
                .expenseLimit(summary.expenseLimit())
                .totalSpent(summary.totalSpent())
                .remainingBalance(summary.remainingBalance())
                .isExceeded(summary.isExceeded())
                .build();

        return ResponseEntity.ok(response);
    }
}