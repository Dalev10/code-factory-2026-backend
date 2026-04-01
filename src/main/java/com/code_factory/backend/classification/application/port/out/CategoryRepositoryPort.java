package com.code_factory.backend.classification.application.port.out;

import com.code_factory.backend.classification.domain.model.Category;
import com.code_factory.backend.classification.domain.model.CategoryType;
import java.util.List;

public interface CategoryRepositoryPort {
    List<Category> findGlobalCategories(String name, CategoryType type);
}