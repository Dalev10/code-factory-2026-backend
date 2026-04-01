package com.code_factory.backend.classification.infrastructure.adapter.out.persistence.repository;

import com.code_factory.backend.classification.infrastructure.adapter.out.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    // Buscamos donde userId sea NULL (Globales)
    List<CategoryEntity> findByUserIdIsNull();
}