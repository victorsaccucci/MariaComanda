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
                dto.name(),
                dto.subType());
    }

    public UserType mapUpdate(UpdateUserTypeInputDTO dto) {
        return new UserType(
                dto.id(),
                dto.name(),
                dto.subType());
    }

    public CreateUserTypeOutputDTO mapCreate(UserType userType) {
        return new CreateUserTypeOutputDTO(
                userType.getId(),
                userType.getName(),
                userType.getSubType()
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
