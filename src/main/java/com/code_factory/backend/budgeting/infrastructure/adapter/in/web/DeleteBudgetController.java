package com.code_factory.backend.budgeting.infrastructure.adapter.in.web;

import com.code_factory.backend.budgeting.application.port.in.DeleteBudgetUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class DeleteBudgetController {

    private final DeleteBudgetUseCase deleteBudgetUseCase;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBudget(@PathVariable UUID id) {

        boolean deleted = deleteBudgetUseCase.deleteById(id);

        if (!deleted) {
            return ResponseEntity
                    .badRequest()
                    .body("No existe un presupuesto para eliminar");
        }

        return ResponseEntity
                .ok("Presupuesto eliminado correctamente");
    }
}