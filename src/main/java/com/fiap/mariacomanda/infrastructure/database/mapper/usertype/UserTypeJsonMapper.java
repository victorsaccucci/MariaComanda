package com.fiap.mariacomanda.infrastructure.database.mapper.usertype;

import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserTypeJson;
import org.springframework.stereotype.Component;

@Component
public class UserTypeJsonMapper {

    public CreateUserTypeInputDTO toCreateInput(CreateUserTypeJson json) {
        return new CreateUserTypeInputDTO(
                json.getId(),
                json.getName(),
                json.getSubType()
        );
    }
}
