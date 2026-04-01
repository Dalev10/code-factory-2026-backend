package com.code_factory.backend.classification.infrastructure.adapter.out.persistence.repository;

import com.code_factory.backend.classification.domain.model.CategoryType;
import com.code_factory.backend.classification.infrastructure.adapter.out.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    @Query("SELECT c FROM CategoryEntity c WHERE c.userId IS NULL " +
        "AND (CAST(:name AS string) IS NULL OR LOWER(CAST(c.name AS string)) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%'))) " +
        "AND (:type IS NULL OR c.type = :type)")
    List<CategoryEntity> findGlobalWithFilters(
            @Param("name") String name, 
            @Param("type") CategoryType type
    );
}