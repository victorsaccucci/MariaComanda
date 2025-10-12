package com.fiap.mariacomanda.infrastructure.config.swagger.openapi.controller;

import com.fiap.mariacomanda.core.dto.usertype.output.CreateUserTypeOutputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.GetUserTypeOutputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserTypeJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateUserTypeJson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

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

    @Operation(summary = "Busca um Tipo de Usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário encontrado")
    })
    @GetMapping("/{id}")
    GetUserTypeOutputDTO get(@PathVariable UUID id);

    @Operation(summary = "Lista os tipos de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tipos de usuário retornada com sucesso")
    })
    @GetMapping
    List<GetUserTypeOutputDTO> list(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size);

    @Operation(summary = "Atualiza os dados de um tipo de usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário atualizado com sucesso")
    })
    @PutMapping("/{id}")
    GetUserTypeOutputDTO update(@RequestHeader("X-Requester-User-Id") UUID requesterUserId,
                                @PathVariable UUID id,
                                @RequestBody UpdateUserTypeJson updateUserTypeJson);

    @Operation(summary = "Exclui um tipo de usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de usuário excluído com sucesso")
    })
    @DeleteMapping("/{id}")
        void delete(@RequestHeader("X-Requester-User-Id") UUID requesterUserId,
                                @PathVariable UUID id);
}
