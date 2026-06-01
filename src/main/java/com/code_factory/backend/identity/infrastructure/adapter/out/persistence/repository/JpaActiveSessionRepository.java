package com.code_factory.backend.identity.infrastructure.adapter.out.persistence.repository;

import com.code_factory.backend.identity.infrastructure.adapter.out.persistence.entity.ActiveSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaActiveSessionRepository extends JpaRepository<ActiveSessionEntity, UUID> {
    Optional<ActiveSessionEntity> findByJti(String jti);
}
