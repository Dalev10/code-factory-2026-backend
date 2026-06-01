package com.code_factory.backend.identity.application.port.out;

import java.util.UUID;

public interface JwtTokenPort {
    String generateToken(UUID userId, String email);
    String extractEmail(String token);
    boolean isTokenValid(String token);
}
