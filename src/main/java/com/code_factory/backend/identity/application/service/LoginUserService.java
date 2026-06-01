package com.code_factory.backend.identity.application.service;

import com.code_factory.backend.identity.application.port.in.LoginUserUseCase;
import com.code_factory.backend.identity.application.port.out.JwtTokenPort;
import com.code_factory.backend.identity.application.port.out.PasswordEncoderPort;
import com.code_factory.backend.identity.application.port.out.SessionRepositoryPort;
import com.code_factory.backend.identity.application.port.out.UserRepositoryPort;
import com.code_factory.backend.identity.domain.exception.AccountBlockedException;
import com.code_factory.backend.identity.domain.exception.InvalidCredentialsException;
import com.code_factory.backend.identity.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginUserService implements LoginUserUseCase {

    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final int BLOCK_DURATION_MINUTES = 15;

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final JwtTokenPort jwtTokenPort;
    private final SessionRepositoryPort sessionRepositoryPort;

    @Override
    public String login(String email, String password) {
        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if (user.getBlockedUntil() != null && user.getBlockedUntil().isAfter(LocalDateTime.now())) {
            throw new AccountBlockedException(user.getBlockedUntil());
        }

        if (!passwordEncoderPort.matches(password, user.getPassword())) {
            int attempts = user.getFailedAttempts() + 1;
            user.setFailedAttempts(attempts);
            if (attempts >= MAX_FAILED_ATTEMPTS) {
                user.setBlockedUntil(LocalDateTime.now().plusMinutes(BLOCK_DURATION_MINUTES));
            }
            userRepositoryPort.save(user);
            throw new InvalidCredentialsException();
        }

        user.setFailedAttempts(0);
        user.setBlockedUntil(null);
        userRepositoryPort.save(user);

        String jti = UUID.randomUUID().toString();
        sessionRepositoryPort.createSession(jti, user.getEmail());
        return jwtTokenPort.generateToken(user.getId(), user.getEmail(), jti);
    }
}
