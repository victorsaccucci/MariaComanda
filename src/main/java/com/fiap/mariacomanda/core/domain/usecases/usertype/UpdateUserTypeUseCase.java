package com.fiap.mariacomanda.core.domain.usecases.usertype;

import com.fiap.mariacomanda.core.domain.entity.UserType;

public interface UpdateUserTypeUseCase {
    UserType execute(UserType user);
}
