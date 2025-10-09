package com.fiap.mariacomanda.infrastructure.database.mapper.usertype;

import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserTypeJson;

public interface UserTypeJsonMapper {

    CreateUserTypeInputDTO toCreateInput(CreateUserTypeJson json);
}
