package com.fiap.mariacomanda.core.usecases.user;

import com.fiap.mariacomanda.core.domain.entity.User;

public interface CreateUserUseCase {
    User execute(User user);
}
