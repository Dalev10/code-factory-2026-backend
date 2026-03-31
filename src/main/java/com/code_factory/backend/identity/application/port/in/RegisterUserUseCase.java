package com.code_factory.backend.identity.application.port.in;

import com.code_factory.backend.identity.domain.model.User;

public interface RegisterUserUseCase {
        User register(User user);
} 

