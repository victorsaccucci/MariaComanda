package com.fiap.mariacomanda.infrastructure.database.mapper.usertype;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.dto.user.output.GetUserOutputDTO;
import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.input.UpdateUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.CreateUserTypeOutputDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserTypeDtoMapper {
    public UserType mapCreate(CreateUserTypeInputDTO dto) {
        return new UserType(
                dto.id(),
                dto.typeName());
    }

    public UserType mapUpdate(UpdateUserTypeInputDTO dto) {
        return new UserType(
                dto.id(),
                dto.typeName());
    }

    public CreateUserTypeOutputDTO mapCreate(UserType user) {
        return new CreateUserTypeOutputDTO(
                user.getId(),
                user.getTypeName()
        );
    }

    public GetUserOutputDTO mapToGetOutputDTO(User user) {
        return new GetUserOutputDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUserTypeId()
        );
    }

    public List<GetUserOutputDTO> mapToGetOutputDTOList(List<User> users) {
        return users.stream().map(this::mapToGetOutputDTO).collect(Collectors.toList());
    }

}
