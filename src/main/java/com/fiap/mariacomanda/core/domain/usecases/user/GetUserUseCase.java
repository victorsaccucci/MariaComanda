package com.fiap.mariacomanda.core.domain.usecases.user;

import com.fiap.mariacomanda.core.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface GetUserUseCase {
    Optional<User> execute(UUID id);
}
