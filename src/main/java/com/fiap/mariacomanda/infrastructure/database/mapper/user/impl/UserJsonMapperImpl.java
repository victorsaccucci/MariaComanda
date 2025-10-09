package com.fiap.mariacomanda.infrastructure.database.mapper.user.impl;

import com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.input.UpdateUserInputDTO;
import com.fiap.mariacomanda.infrastructure.database.mapper.user.UserJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateUserJson;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserJsonMapperImpl implements UserJsonMapper {

    @Override
    public CreateUserInputDTO toCreateInput(CreateUserJson json) {
        return new CreateUserInputDTO(
                json.getName(),
                json.getEmail(),
                json.getPassword(),
                json.getUserTypeId()
        );
    }

    @Override
    public UpdateUserInputDTO toUpdateInput(UUID id, UpdateUserJson json) {
        return new UpdateUserInputDTO(
                id,
                json.getName(),
                json.getEmail(),
                json.getPassword(),
                json.getUserTypeId()
        );
    }
}
