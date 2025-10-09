package com.fiap.mariacomanda.core.domain.usecases.usertype;

import com.fiap.mariacomanda.core.domain.entity.UserType;

import java.util.UUID;

public interface UpdateUserTypeUseCase {
    UserType execute(UserType user, UUID requesterUserId);
}
