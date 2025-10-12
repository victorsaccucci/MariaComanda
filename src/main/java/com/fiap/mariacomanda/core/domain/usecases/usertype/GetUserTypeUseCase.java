package com.fiap.mariacomanda.core.domain.usecases.usertype;

import com.fiap.mariacomanda.core.domain.entity.UserType;

import java.util.Optional;
import java.util.UUID;

public interface GetUserTypeUseCase {
    Optional<UserType> execute(UUID id);
}
