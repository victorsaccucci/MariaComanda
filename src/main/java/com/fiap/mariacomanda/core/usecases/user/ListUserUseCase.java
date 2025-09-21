package com.fiap.mariacomanda.core.usecases.user;

import com.fiap.mariacomanda.core.domain.entity.User;

import java.util.List;

public interface ListUserUseCase {
    List<User> execute(int page, int size);
}
