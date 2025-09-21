package com.fiap.mariacomanda.core.domain.usecases.user;

import com.fiap.mariacomanda.core.domain.entity.User;

public interface CreateUserUseCase {
    User execute(User user);
}
