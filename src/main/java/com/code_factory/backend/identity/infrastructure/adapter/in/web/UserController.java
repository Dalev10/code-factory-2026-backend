package com.code_factory.backend.identity.infrastructure.adapter.in.web;

import com.code_factory.backend.identity.application.port.in.RegisterUserUseCase;
import com.code_factory.backend.identity.domain.model.User;
import com.code_factory.backend.identity.infrastructure.adapter.in.web.dto.UserRegistrationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationRequest request) {
        
        // Validación de que las contraseñas coincidan
        if (!request.getPassword().equals(request.getPasswordConfirmation())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Las contraseñas no coinciden");
        }

        // Mapeo manual del DTO al Modelo de Dominio
        User userToRegister = User.builder()
                .id(UUID.randomUUID()) // Generamos el ID técnico aquí
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword()) // ¡Ojo! Aquí falta cifrarla, lo haremos luego.
                .build();

        User registeredUser = registerUserUseCase.register(userToRegister);

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }
}