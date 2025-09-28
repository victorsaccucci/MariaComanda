package com.fiap.mariacomanda.core.mapper;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.input.UpdateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.output.CreateUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.output.GetUserOutputDTO;

import java.util.List;

public interface UserMapper {

    User mapCreate(CreateUserInputDTO dto);

    User mapUpdate(UpdateUserInputDTO dto);

    CreateUserOutputDTO mapCreate(User user);

    GetUserOutputDTO mapToGetOutputDTO(User user);

    List<GetUserOutputDTO> mapToGetOutputDTOList(List<User> users);

}
