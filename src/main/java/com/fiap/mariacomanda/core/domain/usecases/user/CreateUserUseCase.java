package com.fiap.mariacomanda.core.domain.usecases.user;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO;

import java.util.UUID;

public interface CreateUserUseCase {
    User execute(CreateUserInputDTO inputDTO, UUID requesterUserId);
}
