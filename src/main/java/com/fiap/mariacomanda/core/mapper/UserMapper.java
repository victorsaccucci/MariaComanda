package com.fiap.mariacomanda.core.mapper;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.output.CreateUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.output.GetUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.input.UpdateUsersInputDTO;

import java.util.List;

public interface UserMapper {

    // Create
    User toDomain(CreateUserInputDTO dto);
    CreateUserOutputDTO toCreateOutput(User user);

    // Get
    GetUserOutputDTO toGetOutput(User user);
    List<GetUserOutputDTO> toGetOutputList(List<User> users);

    // Update
    User toDomain(UpdateUsersInputDTO dto);
}
