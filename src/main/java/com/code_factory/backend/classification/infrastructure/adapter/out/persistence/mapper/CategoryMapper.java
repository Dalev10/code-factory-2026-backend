package com.code_factory.backend.classification.infrastructure.adapter.out.persistence.mapper;

import com.code_factory.backend.classification.domain.model.Category;
import com.code_factory.backend.classification.infrastructure.adapter.out.persistence.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryEntity toEntity(Category domain) {
        if (domain == null) return null;
        return CategoryEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .type(domain.getType())
                .userId(domain.getUserId())
                .build();
    }

    public Category toDomain(CategoryEntity entity) {
        if (entity == null) return null;
        return Category.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .userId(entity.getUserId())
                .build();
    }
}