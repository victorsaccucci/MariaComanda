package com.fiap.mariacomanda.infrastructure.database.mapper.user;

import com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserJson;

public interface UserJsonMapper {

    CreateUserInputDTO toCreateInput(CreateUserJson json);
}
