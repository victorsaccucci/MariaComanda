package com.fiap.mariacomanda.infrastructure.database.mapper.usertype;

import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.input.UpdateUserTypeInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserTypeJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateUserTypeJson;

import java.util.UUID;

public interface UserTypeJsonMapper {

    CreateUserTypeInputDTO toCreateInput(CreateUserTypeJson json);

    UpdateUserTypeInputDTO toUpdateInput(UUID id, UpdateUserTypeJson json);
}
