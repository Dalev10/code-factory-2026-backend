package com.code_factory.backend.identity.infrastructure.adapter.out.persistence.repository;

import com.code_factory.backend.identity.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE " +
           "(:email IS NULL OR LOWER(CAST(u.email AS string)) LIKE LOWER(CONCAT('%', CAST(:email AS string), '%'))) AND " +
           "(:firstName IS NULL OR LOWER(CAST(u.firstName AS string)) LIKE LOWER(CONCAT('%', CAST(:firstName AS string), '%'))) AND " +
           "(:lastName IS NULL OR LOWER(CAST(u.lastName AS string)) LIKE LOWER(CONCAT('%', CAST(:lastName AS string), '%')))")
    List<UserEntity> findWithFilters(@Param("email") String email, @Param("firstName") String firstName, @Param("lastName") String lastName);
}