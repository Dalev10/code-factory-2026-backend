package com.code_factory.backend.identity.application.port.in;

import com.code_factory.backend.identity.domain.model.User;
import java.util.List;

public interface FindUserUseCase {
    List<User> findUsers(String email, String firstName, String lastName);
}