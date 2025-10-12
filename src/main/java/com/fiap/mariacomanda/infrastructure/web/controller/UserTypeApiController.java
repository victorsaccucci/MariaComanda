package com.fiap.mariacomanda.infrastructure.web.controller;

import com.fiap.mariacomanda.core.adapters.controller.UserTypeController;
import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.input.DeleteUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.input.GetUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.input.ListUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.input.UpdateUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.CreateUserTypeOutputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.GetUserTypeOutputDTO;
import com.fiap.mariacomanda.infrastructure.config.swagger.openapi.controller.UserTypeApi;
import com.fiap.mariacomanda.infrastructure.database.mapper.usertype.UserTypeJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserTypeJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateUserTypeJson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    public GetUserTypeOutputDTO get(@PathVariable UUID id) {
        GetUserTypeInputDTO inputDTO = new GetUserTypeInputDTO(id);
        return userTypeController.get(inputDTO);
    }

    public List<GetUserTypeOutputDTO> list(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size) {
        ListUserTypeInputDTO inputDTO = new ListUserTypeInputDTO(page, size);
        return userTypeController.list(inputDTO);
    }

    public GetUserTypeOutputDTO update(@RequestHeader("X-Requester-User-Id") UUID requesterUserId,
                                    @PathVariable UUID id,
                                    @Valid @RequestBody UpdateUserTypeJson updateUserTypeJson) {
        UpdateUserTypeInputDTO inputDTO = userTypeJsonMapper.toUpdateInput(id, updateUserTypeJson);
        return userTypeController.update(inputDTO, requesterUserId);
    }

    public void delete(@RequestHeader("X-Requester-User-Id") UUID requesterUserId,
                       @PathVariable UUID id) {
        DeleteUserTypeInputDTO inputDTO = new DeleteUserTypeInputDTO(id);
        userTypeController.delete(inputDTO, requesterUserId);
    }

}
