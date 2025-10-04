package com.fiap.mariacomanda.infrastructure.database.mapper.usertype;

import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.input.UpdateUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.CreateUserTypeOutputDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class UserTypeDtoMapper {
    public UserType toDomain(CreateUserTypeInputDTO dto) {
        return buildUserType(null, dto.name(), dto.subType());
    }

    public UserType toDomain(UpdateUserTypeInputDTO dto) {
        return buildUserType(dto.id(), dto.name(), dto.subType());
    }

    public CreateUserTypeOutputDTO toCreateOutput(UserType userType) {
        return new CreateUserTypeOutputDTO(
                userType.getId(),
                userType.getName(),
                userType.getSubType()
        );
    }

    private UserType buildUserType(UUID id, String name, String subType) {
        return id == null ? new UserType(name, subType) : new UserType(id, name, subType);
    }

}
