package com.fiap.mariacomanda.infrastructure.config.swagger.openapi.controller;

import com.fiap.mariacomanda.core.dto.restaurant.output.GetRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.user.input.UpdateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.output.CreateUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.output.GetUserOutputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserJson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Usuário", description = "Operações relacionada ao usuário")
@RequestMapping("/api/user")
public interface UserApi {

    @Operation(summary = "Cadastrar um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário acadastrado com sucesso")
    })
    @PostMapping
    CreateUserOutputDTO createUser(@RequestBody CreateUserJson createUserJson);

    @Operation(summary = "Busca um Usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    })
    @GetMapping("/{id}")
    GetUserOutputDTO get(@PathVariable UUID id);

    @Operation(summary = "Lista os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    })
    @GetMapping
    List<GetUserOutputDTO> list(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "20") int size);

    @Operation(summary = "Atualiza os dados de um usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    })
    @PutMapping("/{id}")
    GetUserOutputDTO update(@PathVariable UUID id, @RequestBody UpdateUserInputDTO dto);

    @Operation(summary = "Exclui um usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso")
    })
    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id);
}
