package com.code_factory.backend.identity.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationRequest {

    @NotBlank(message = "El nombre es obligatorio") 
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres") 
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio") 
    @Size(min = 2, message = "El apellido debe tener al menos 2 caracteres") 
    private String lastName;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Formato de correo inválido") 
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).+$",
        message = "La contraseña debe contener al menos una mayúscula, una minúscula, un número y un carácter especial"
    )
    private String password;

    @NotBlank(message = "Debes confirmar la contraseña")
    private String passwordConfirmation;
}