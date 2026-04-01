package com.code_factory.backend.classification.application.service;

import com.code_factory.backend.classification.application.port.in.ListCategoriesUseCase;
import com.code_factory.backend.classification.application.port.out.CategoryRepositoryPort;
import com.code_factory.backend.classification.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListCategoriesService implements ListCategoriesUseCase {

    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public List<Category> getAllGlobalCategories() {
        return categoryRepositoryPort.findGlobalCategories();
    }
}