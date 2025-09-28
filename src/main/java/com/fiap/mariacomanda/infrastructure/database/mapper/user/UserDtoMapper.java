package com.fiap.mariacomanda.infrastructure.database.mapper.user;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.input.UpdateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.output.CreateUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.output.GetUserOutputDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoMapper {
    public User mapCreate(CreateUserInputDTO dto) {
        return new User(
                dto.id(),
                dto.name(),
                dto.email(),
                dto.password(),
                dto.userTypeId());
    }

    public User mapUpdate(UpdateUserInputDTO dto) {
        return new User(
                dto.id(),
                dto.name(),
                dto.email(),
                dto.password(),
                dto.userTypeId());
    }

    public CreateUserOutputDTO mapCreate(User user) {
        return new CreateUserOutputDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUserTypeId()
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
