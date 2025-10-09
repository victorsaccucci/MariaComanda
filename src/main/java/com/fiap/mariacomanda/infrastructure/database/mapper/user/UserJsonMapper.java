package com.fiap.mariacomanda.infrastructure.database.mapper.user;

import com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.input.UpdateUserInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateUserJson;

import java.util.UUID;

public interface UserJsonMapper {

    CreateUserInputDTO toCreateInput(CreateUserJson json);

    UpdateUserInputDTO toUpdateInput(UUID id, UpdateUserJson json);
}
