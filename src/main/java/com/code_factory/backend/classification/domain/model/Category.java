package com.code_factory.backend.classification.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private UUID id;
    private String name;
    private CategoryType type;
    private UUID userId; // Será null para las categorías pre-establecidas (globales)
}