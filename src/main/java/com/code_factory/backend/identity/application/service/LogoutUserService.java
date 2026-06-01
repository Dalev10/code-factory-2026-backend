package com.code_factory.backend.identity.application.service;

import com.code_factory.backend.identity.application.port.in.LogoutUserUseCase;
import com.code_factory.backend.identity.application.port.out.JwtTokenPort;
import com.code_factory.backend.identity.application.port.out.SessionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutUserService implements LogoutUserUseCase {

    private final JwtTokenPort jwtTokenPort;
    private final SessionRepositoryPort sessionRepositoryPort;

    @Override
    public void logout(String token) {
        String jti = jwtTokenPort.extractJti(token);
        sessionRepositoryPort.revokeSession(jti);
    }
}
