package com.fiap.mariacomanda.core.usecases.userType;

import com.fiap.mariacomanda.core.domain.entity.UserType;

public interface CreateUserTypeUserCase {
    UserType execute(UserType userType);
}
