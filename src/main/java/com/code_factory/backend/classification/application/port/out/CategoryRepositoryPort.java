package com.code_factory.backend.classification.application.port.out;

import com.code_factory.backend.classification.domain.model.Category;
import java.util.List;

public interface CategoryRepositoryPort {
    List<Category> findGlobalCategories();
}