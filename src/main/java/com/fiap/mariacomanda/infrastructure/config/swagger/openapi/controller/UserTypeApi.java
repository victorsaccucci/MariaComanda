package com.fiap.mariacomanda.infrastructure.config.swagger.openapi.controller;

import com.fiap.mariacomanda.core.dto.usertype.output.CreateUserTypeOutputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserTypeJson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Tipo de Usuário", description = "Operações relacionada ao tipo de usuário")
@RequestMapping("/api/user-type")
public interface UserTypeApi {

    @Operation(summary = "Cadastrar um novo tipo de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
    })
    @PostMapping
    CreateUserTypeOutputDTO createUser(
            @RequestHeader("X-Requester-User-Id") UUID requesterUserId,
            @RequestBody CreateUserTypeJson createUserTypeJson);
}
