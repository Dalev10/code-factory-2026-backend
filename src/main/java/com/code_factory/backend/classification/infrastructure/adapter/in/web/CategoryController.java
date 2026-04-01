package com.code_factory.backend.classification.infrastructure.adapter.in.web;

import com.code_factory.backend.classification.application.port.in.ListCategoriesUseCase;
import com.code_factory.backend.classification.domain.model.Category;
import com.code_factory.backend.classification.domain.model.CategoryType;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ListCategoriesUseCase listCategoriesUseCase;

    @GetMapping
    public ResponseEntity<List<Category>> getCategories(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) CategoryType type) {
        
        return ResponseEntity.ok(listCategoriesUseCase.getCategories(name, type));
    }
}