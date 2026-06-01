package com.code_factory.backend.identity.domain.exception;

import java.time.LocalDateTime;

public class AccountBlockedException extends RuntimeException {

    public AccountBlockedException(LocalDateTime blockedUntil) {
        super("La cuenta está bloqueada temporalmente. Intente de nuevo después de las " + blockedUntil);
    }
}
