package com.fiap.mariacomanda.infrastructure.web.controller;

import com.fiap.mariacomanda.core.adapters.controller.UserController;
import com.fiap.mariacomanda.core.dto.user.input.*;
import com.fiap.mariacomanda.core.dto.user.output.CreateUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.output.GetUserOutputDTO;
import com.fiap.mariacomanda.infrastructure.config.swagger.openapi.controller.UserApi;
import com.fiap.mariacomanda.infrastructure.database.mapper.user.UserJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateUserJson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserApiController implements UserApi {

    private final UserController userController;

    private final UserJsonMapper userJsonMapper;

    public CreateUserOutputDTO createUser(@RequestHeader("X-Requester-User-Id") UUID requesterUserId,
                                        @Valid @RequestBody CreateUserJson createUserJson) {
        CreateUserInputDTO inputDTO = userJsonMapper.toCreateInput(createUserJson);
        return userController.create(inputDTO, requesterUserId);
    }

    public GetUserOutputDTO get(@PathVariable UUID id) {
        GetUserInputDTO inputDTO = new GetUserInputDTO(id);
        return userController.get(inputDTO);
    }

    public List<GetUserOutputDTO> list(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size) {
        ListUserInputDTO inputDTO = new ListUserInputDTO(page, size);
        return userController.list(inputDTO);
    }

    public GetUserOutputDTO update(@RequestHeader("X-Requester-User-Id") UUID requesterUserId,
                                @PathVariable UUID id,
                                @Valid @RequestBody UpdateUserJson updateUserJson) {
        UpdateUserInputDTO inputDTO = userJsonMapper.toUpdateInput(id, updateUserJson);
        return userController.update(inputDTO, requesterUserId);
    }

    public void delete(@RequestHeader("X-Requester-User-Id") UUID requesterUserId,
                       @PathVariable UUID id) {
        DeleteUserInputDTO inputDTO = new DeleteUserInputDTO(id);
        userController.delete(inputDTO, requesterUserId);
    }
}
