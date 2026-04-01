package com.code_factory.backend.classification.infrastructure.adapter.out.persistence.adapter;

import com.code_factory.backend.classification.application.port.out.CategoryRepositoryPort;
import com.code_factory.backend.classification.domain.model.Category;
import com.code_factory.backend.classification.infrastructure.adapter.out.persistence.mapper.CategoryMapper;
import com.code_factory.backend.classification.infrastructure.adapter.out.persistence.repository.JpaCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements CategoryRepositoryPort {

    private final JpaCategoryRepository jpaCategoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<Category> findGlobalCategories() {
        return jpaCategoryRepository.findByUserIdIsNull()
                .stream()
                .map(categoryMapper::toDomain)
                .collect(Collectors.toList());
    }
}