package com.code_factory.backend.identity.application.port.out;

import com.code_factory.backend.identity.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    boolean existsById(UUID id);
    boolean existsByEmail(String email);
    List<User> findAllFiltered(String email, String firstName, String lastName);
    
}
