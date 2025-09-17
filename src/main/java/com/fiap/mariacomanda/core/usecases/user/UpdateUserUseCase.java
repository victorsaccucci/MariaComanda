package com.fiap.mariacomanda.core.usecases.user;

import com.fiap.mariacomanda.core.domain.entity.User;

public interface UpdateUserUseCase {
    User execute(User user);
}
