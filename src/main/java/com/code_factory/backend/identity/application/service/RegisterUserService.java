package com.code_factory.backend.identity.application.service;

import com.code_factory.backend.identity.application.port.in.RegisterUserUseCase;
import com.code_factory.backend.identity.application.port.out.UserRepositoryPort;
import com.code_factory.backend.identity.domain.model.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public User register(User user) {
        // Validar si el correo ya existe
        if (userRepositoryPort.existsByEmail(user.getEmail())) {
            // Por ahora lanzamos una excepción genérica, 
            // luego crearemos nuestra excepción de dominio personalizada.
            throw new RuntimeException("Email already in use: " + user.getEmail());
        }

        // Aquí más adelante cifraremos la contraseña antes de guardar
        return userRepositoryPort.save(user);
    }
}