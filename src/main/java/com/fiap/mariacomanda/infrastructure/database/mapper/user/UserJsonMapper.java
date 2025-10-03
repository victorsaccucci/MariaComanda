package com.fiap.mariacomanda.infrastructure.database.mapper.user;

import com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserJson;
import org.springframework.stereotype.Component;

@Component
public class UserJsonMapper {

    public CreateUserInputDTO toCreateInput(CreateUserJson json) {
        return new CreateUserInputDTO(
                json.getId(),
                json.getName(),
                json.getEmail(),
                json.getPassword(),
                json.getUserTypeId()
        );
    }
}
