package com.fiap.mariacomanda.infrastructure.database.mapper.user;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.input.UpdateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.output.CreateUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.output.GetUserOutputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.GetUserTypeOutputDTO;
import com.fiap.mariacomanda.core.mapper.UserTypeMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoMapper {

    private final UserTypeMapper userTypeMapper;

    public UserDtoMapper(UserTypeMapper userTypeMapper) {
        this.userTypeMapper = userTypeMapper;
    }

    public User toDomain(CreateUserInputDTO dto) {
        return new User(
                dto.id(),
                dto.name(),
                dto.email(),
                dto.password(),
                UserType.reference(dto.userTypeId()));
    }

    public User toDomain(UpdateUserInputDTO dto) {
        return new User(
                dto.id(),
                dto.name(),
                dto.email(),
                dto.password(),
                UserType.reference(dto.userTypeId()));
    }

    public CreateUserOutputDTO toCreateOutput(User user) {
        GetUserTypeOutputDTO userTypeOutput = user.getUserType() != null ? userTypeMapper.toGetOutput(user.getUserType()) : null;
        return new CreateUserOutputDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                userTypeOutput
        );
    }

    public GetUserOutputDTO toGetOutput(User user) {
        GetUserTypeOutputDTO userTypeOutput = user.getUserType() != null ? userTypeMapper.toGetOutput(user.getUserType()) : null;
        return new GetUserOutputDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                userTypeOutput
        );
    }

    public List<GetUserOutputDTO> toGetOutputList(List<User> users) {
        return users.stream().map(this::toGetOutput).collect(Collectors.toList());
    }

}
