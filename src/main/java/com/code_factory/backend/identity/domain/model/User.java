package com.code_factory.backend.identity.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Entidad de Dominio: User
 * Representa al usuario central del sistema PGLI (Plataforma de Gestión de Liquidez Individual)
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID id; 
    private String firstName;
    private String lastName;
    private String email; 
    private String password;     
    
}
