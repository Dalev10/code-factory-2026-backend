package com.code_factory.backend.classification.application.port.in;

import com.code_factory.backend.classification.domain.model.Category;
import com.code_factory.backend.classification.domain.model.CategoryType;

import java.util.List;

public interface ListCategoriesUseCase {
    List<Category> getCategories(String name, CategoryType type);
}