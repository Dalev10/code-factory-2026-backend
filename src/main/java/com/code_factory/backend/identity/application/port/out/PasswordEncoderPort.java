package com.code_factory.backend.identity.application.port.out;

public interface PasswordEncoderPort {
    String encode(String rawPassword);
}