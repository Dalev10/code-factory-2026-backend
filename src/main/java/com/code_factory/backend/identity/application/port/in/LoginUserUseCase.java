package com.code_factory.backend.identity.application.port.in;

public interface LoginUserUseCase {
    String login(String email, String password);
}
