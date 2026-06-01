package com.code_factory.backend.identity.application.port.out;

import com.code_factory.backend.identity.application.port.out.dto.SessionInfo;

import java.util.Optional;

public interface SessionRepositoryPort {
    void createSession(String jti, String userEmail);
    Optional<SessionInfo> findByJti(String jti);
    void revokeSession(String jti);
    void updateLastActivity(String jti);
}
