package com.fiap.mariacomanda.infrastructure.web.controller;

import com.fiap.mariacomanda.core.adapters.controller.UserTypeController;
import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.CreateUserTypeOutputDTO;
import com.fiap.mariacomanda.infrastructure.config.swagger.openapi.controller.UserTypeApi;
import com.fiap.mariacomanda.infrastructure.database.mapper.usertype.UserTypeJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserTypeJson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserTypeApiController implements UserTypeApi {

    private final UserTypeController userTypeController;

    private final UserTypeJsonMapper userTypeJsonMapper;

    public CreateUserTypeOutputDTO createUser(@RequestHeader("X-Requester-User-Id") UUID requesterUserId,
                                              @Valid @RequestBody CreateUserTypeJson createUserTypeJson) {
        CreateUserTypeInputDTO inputDTO = userTypeJsonMapper.toCreateInput(createUserTypeJson);
        return userTypeController.create(inputDTO, requesterUserId);
    }

}
