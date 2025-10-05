package com.fiap.mariacomanda.core.domain.usecases.usertype;

import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;

import java.util.UUID;

public interface CreateUserTypeUseCase {
    UserType execute(CreateUserTypeInputDTO inputDTO, UUID requesterUserId);
}
