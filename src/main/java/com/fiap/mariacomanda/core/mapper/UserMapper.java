package com.fiap.mariacomanda.core.mapper;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.input.UpdateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.output.CreateUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.output.GetUserOutputDTO;

import java.util.List;

public interface UserMapper {

    User toDomain(CreateUserInputDTO dto, UserType userType);

    User toDomain(UpdateUserInputDTO dto, UserType userType);

    CreateUserOutputDTO toCreateOutput(User user);

    GetUserOutputDTO toGetOutput(User user);

    List<GetUserOutputDTO> toGetOutputList(List<User> users);

}
