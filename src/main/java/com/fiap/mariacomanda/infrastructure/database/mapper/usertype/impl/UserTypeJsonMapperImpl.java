package com.fiap.mariacomanda.infrastructure.database.mapper.usertype.impl;

import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;
import com.fiap.mariacomanda.infrastructure.database.mapper.usertype.UserTypeJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserTypeJson;
import org.springframework.stereotype.Component;

@Component
public class UserTypeJsonMapperImpl implements UserTypeJsonMapper {

    public CreateUserTypeInputDTO toCreateInput(CreateUserTypeJson json) {
        return new CreateUserTypeInputDTO(
                json.getName(),
                json.getSubType()
        );
    }
}
