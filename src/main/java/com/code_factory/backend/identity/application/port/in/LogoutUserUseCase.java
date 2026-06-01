package com.code_factory.backend.identity.application.port.in;

public interface LogoutUserUseCase {
    void logout(String token);
}
