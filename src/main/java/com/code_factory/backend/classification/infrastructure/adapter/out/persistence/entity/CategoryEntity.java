package com.code_factory.backend.classification.infrastructure.adapter.out.persistence.entity;

import com.code_factory.backend.classification.domain.model.CategoryType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 100, columnDefinition = "varchar(100)")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategoryType type;

    @Column(name = "user_id")
    private UUID userId; // Null para categorías globales
}