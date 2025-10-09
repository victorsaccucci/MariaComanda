package com.fiap.mariacomanda.core.domain.usecases.user;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.dto.user.input.UpdateUserInputDTO;

import java.util.UUID;

public interface UpdateUserUseCase {
    User execute(UpdateUserInputDTO inputDTO, UUID requesterUserId);
}
