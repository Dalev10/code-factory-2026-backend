package com.code_factory.backend.budgeting.infrastructure.adapter.in.web;

import com.code_factory.backend.budgeting.application.port.in.CreateBudgetAllocationCommand;
import com.code_factory.backend.budgeting.application.port.in.CreateBudgetAllocationUseCase;
import com.code_factory.backend.budgeting.application.port.in.UpdateBudgetAllocationCommand;
import com.code_factory.backend.budgeting.application.port.in.UpdateBudgetAllocationUseCase;
import com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto.BudgetAllocationResponse;
import com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto.CreateBudgetAllocationRequest;
import com.code_factory.backend.budgeting.infrastructure.adapter.in.web.dto.UpdateBudgetAllocationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/budgets/{budgetId}/allocations")
@RequiredArgsConstructor
public class BudgetAllocationController {

    private final CreateBudgetAllocationUseCase createBudgetAllocationUseCase;
    private final UpdateBudgetAllocationUseCase updateBudgetAllocationUseCase;

    // Escenarios 1 y 2: Crear asignación y validar saldo disponible
    @PostMapping
    public ResponseEntity<BudgetAllocationResponse> createAllocation(
            @PathVariable UUID budgetId,
            @Valid @RequestBody CreateBudgetAllocationRequest request) {

        var command = new CreateBudgetAllocationCommand(
                request.getUserId(),
                budgetId,
                request.getCategoryId(),
                request.getAmount()
        );

        var allocation = createBudgetAllocationUseCase.createAllocation(command);

        var response = BudgetAllocationResponse.builder()
                .id(allocation.getId())
                .budgetId(allocation.getBudgetId())
                .categoryId(allocation.getCategoryId())
                .amount(allocation.getAmount())
                .createdAt(allocation.getCreatedAt())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Escenario 3: Editar asignación existente
    @PutMapping("/{categoryId}")
    public ResponseEntity<BudgetAllocationResponse> updateAllocation(
            @PathVariable UUID budgetId,
            @PathVariable UUID categoryId,
            @Valid @RequestBody UpdateBudgetAllocationRequest request) {

        var command = new UpdateBudgetAllocationCommand(
                request.getUserId(),
                budgetId,
                categoryId,
                request.getNewAmount()
        );

        var allocation = updateBudgetAllocationUseCase.updateAllocation(command);

        var response = BudgetAllocationResponse.builder()
                .id(allocation.getId())
                .budgetId(allocation.getBudgetId())
                .categoryId(allocation.getCategoryId())
                .amount(allocation.getAmount())
                .createdAt(allocation.getCreatedAt())
                .build();

        return ResponseEntity.ok(response);
    }
}