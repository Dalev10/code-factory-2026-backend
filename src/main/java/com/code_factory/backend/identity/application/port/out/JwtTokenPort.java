package com.code_factory.backend.identity.application.port.out;

import java.util.UUID;

public interface JwtTokenPort {
    String generateToken(UUID userId, String email, String jti);
    String extractEmail(String token);
    String extractJti(String token);
    boolean isTokenValid(String token);
}
