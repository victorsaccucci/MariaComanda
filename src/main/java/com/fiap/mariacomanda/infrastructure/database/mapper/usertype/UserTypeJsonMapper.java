package com.fiap.mariacomanda.infrastructure.database.mapper.usertype;

import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserTypeJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateUserTypeJson;
import com.fiap.mariacomanda.core.dto.usertype.input.UpdateUserTypeInputDTO;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UserTypeJsonMapper {

    public CreateUserTypeInputDTO toCreateInput(CreateUserTypeJson json) {
        return new CreateUserTypeInputDTO(
                json.getName(),
                json.getSubType()
        );
    }

    public UpdateUserTypeInputDTO toUpdateInput(UUID id, UpdateUserTypeJson json) {
        return new UpdateUserTypeInputDTO(
                id,
                json.getName(),
                json.getSubType()
        );
    }
}
