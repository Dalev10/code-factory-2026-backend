package com.code_factory.backend.transaction.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateTransactionCategoryRequest(

        @NotNull(message = "La categoría es obligatoria")
        UUID categoryId

) {
}