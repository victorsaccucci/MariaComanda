package com.fiap.mariacomanda.core.mapper;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.dto.user.CreateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.CreateUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.GetUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.UpdateUsersInputDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public User mapCreateInputToDomain(CreateUserInputDTO dto) {
        return new User(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getPasswordHash(),
                dto.getUserType()
        );
    }

    public CreateUserOutputDTO mapCreateDomainToOutput(User user) {
        return new CreateUserOutputDTO(
                user.id(),
                user.name(),
                user.email(),
                user.passwordHash(),
                user.userType()
        );
    }

    public GetUserOutputDTO mapGetDomainToOutput(User user) {
        return new GetUserOutputDTO(
                user.id(),
                user.name(),
                user.email(),
                user.passwordHash(),
                user.userType()
        );
    }

    public List<GetUserOutputDTO> mapGetMenuItemOutputDtoToList(List<User> users) {
        return users.stream().map(this::mapGetDomainToOutput).collect(Collectors.toList());
    }

    public User mapUpdateInputToDomain(UpdateUsersInputDTO dto) {
        return new User(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getPasswordHash(),
                dto.getUserType()
        );
    }
}
