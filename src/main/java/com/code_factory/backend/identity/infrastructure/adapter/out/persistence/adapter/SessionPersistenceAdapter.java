package com.code_factory.backend.identity.infrastructure.adapter.out.persistence.adapter;

import com.code_factory.backend.identity.application.port.out.SessionRepositoryPort;
import com.code_factory.backend.identity.application.port.out.dto.SessionInfo;
import com.code_factory.backend.identity.infrastructure.adapter.out.persistence.entity.ActiveSessionEntity;
import com.code_factory.backend.identity.infrastructure.adapter.out.persistence.repository.JpaActiveSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SessionPersistenceAdapter implements SessionRepositoryPort {

    private final JpaActiveSessionRepository repository;

    @Override
    public void createSession(String jti, String userEmail) {
        ActiveSessionEntity session = ActiveSessionEntity.builder()
                .jti(jti)
                .userEmail(userEmail)
                .lastActivityAt(LocalDateTime.now())
                .revoked(false)
                .build();
        repository.save(session);
    }

    @Override
    public Optional<SessionInfo> findByJti(String jti) {
        return repository.findByJti(jti)
                .map(e -> new SessionInfo(e.getJti(), e.getUserEmail(), e.getLastActivityAt(), e.isRevoked()));
    }

    @Override
    public void revokeSession(String jti) {
        repository.findByJti(jti).ifPresent(session -> {
            session.setRevoked(true);
            repository.save(session);
        });
    }

    @Override
    public void updateLastActivity(String jti) {
        repository.findByJti(jti).ifPresent(session -> {
            session.setLastActivityAt(LocalDateTime.now());
            repository.save(session);
        });
    }
}
