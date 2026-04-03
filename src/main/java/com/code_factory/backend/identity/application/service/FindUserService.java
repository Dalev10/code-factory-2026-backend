package com.code_factory.backend.identity.application.service;

import com.code_factory.backend.identity.application.port.in.FindUserUseCase;
import com.code_factory.backend.identity.application.port.out.UserRepositoryPort;
import com.code_factory.backend.identity.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindUserService implements FindUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public List<User> findUsers(String email, String firstName, String lastName) {
        return userRepositoryPort.findAllFiltered(email, firstName, lastName);
    }
}