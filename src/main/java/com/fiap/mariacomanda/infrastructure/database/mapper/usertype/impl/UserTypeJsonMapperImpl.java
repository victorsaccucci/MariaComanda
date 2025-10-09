package com.fiap.mariacomanda.infrastructure.database.mapper.usertype.impl;

import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.input.UpdateUserTypeInputDTO;
import com.fiap.mariacomanda.infrastructure.database.mapper.usertype.UserTypeJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserTypeJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateUserTypeJson;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserTypeJsonMapperImpl implements UserTypeJsonMapper {

    @Override
    public CreateUserTypeInputDTO toCreateInput(CreateUserTypeJson json) {
        return new CreateUserTypeInputDTO(
                json.getName(),
                json.getSubType()
        );
    }

    @Override
    public UpdateUserTypeInputDTO toUpdateInput(UUID id, UpdateUserTypeJson json) {
        return new UpdateUserTypeInputDTO(
                id,
                json.getName(),
                json.getSubType()
        );
    }
}
