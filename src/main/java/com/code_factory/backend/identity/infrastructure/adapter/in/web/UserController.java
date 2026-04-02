package com.code_factory.backend.identity.infrastructure.adapter.in.web;

import com.code_factory.backend.identity.application.port.in.RegisterUserUseCase;
import com.code_factory.backend.identity.domain.model.User;
import com.code_factory.backend.identity.infrastructure.adapter.in.web.dto.UserRegistrationRequest;
import com.code_factory.backend.identity.application.port.in.FindUserUseCase;
import com.code_factory.backend.identity.infrastructure.adapter.in.web.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final FindUserUseCase findUserUseCase;

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


        @GetMapping("/users") // Refactorizado de /search a /users
        public ResponseEntity<List<UserResponse>> getUsers(
                @RequestParam(required = false) String email,
                @RequestParam(required = false) String firstName,
                @RequestParam(required = false) String lastName) {
        
        List<UserResponse> users = findUserUseCase.findUsers(email, firstName, lastName)
                .stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .build())
                .toList();

        return ResponseEntity.ok(users);

        }

}

