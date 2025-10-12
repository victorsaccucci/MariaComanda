package com.fiap.mariacomanda.infrastructure.config.swagger.openapi.controller;

import com.fiap.mariacomanda.core.dto.restaurant.output.CreateRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.GetRestaurantOutputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateRestaurantJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateRestaurantJson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Restaurants", description = "Operações relacionada ao restaurante")
@RequestMapping("/api/restaurants")
public interface RestaurantApi {

    @Operation(summary = "Cria um novo restaurante", 
               description = "Cria um novo restaurante com validações de horário de funcionamento e tipo de culinária. " +
                           "O horário deve estar no formato HH:MM-HH:MM ou com dias da semana (ex: SEG-SEX:09:00-22:00). " +
                           "O tipo de culinária deve ser um dos valores pré-definidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso. Retorna apenas o ID do restaurante criado."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos. Possíveis erros: formato de horário inválido, tipo de culinária não suportado, campos obrigatórios ausentes."),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para criar restaurantes (deve ser do tipo OWNER)."),
            @ApiResponse(responseCode = "404", description = "Usuário proprietário não encontrado.")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateRestaurantOutputDTO create(@RequestHeader("X-Requester-User-Id") UUID requesterUserId, @RequestBody CreateRestaurantJson restaurantJson);

    @Operation(summary = "Busca um restaurante pelo ID", 
               description = "Retorna os dados de um restaurante específico baseado no seu UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/BadRequest"),
            @ApiResponse(responseCode = "404", ref = "#/components/responses/NotFound")
    })
    @GetMapping("/{id}")
    GetRestaurantOutputDTO get(@PathVariable("id") @io.swagger.v3.oas.annotations.Parameter(
            description = "UUID do restaurante", 
            example = "550e8400-e29b-41d4-a716-446655440000") UUID id);

    @Operation(summary = "Lista os restaurantes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de restaurantes retornada com sucesso")
    })
    @GetMapping
    List<GetRestaurantOutputDTO> list(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "20") int size);

    @Operation(summary = "Atualiza os dados de um restaurante pelo ID",
               description = "Atualiza um restaurante existente. Todos os campos são opcionais. " +
                           "Aplica as mesmas validações de criação para horário e tipo de culinária.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou formato incorreto"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão ou não é o proprietário do restaurante"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
        @PutMapping("/{id}")
                GetRestaurantOutputDTO update(@RequestHeader("X-Requester-User-Id") UUID requesterUserId,
                                                                          @PathVariable UUID id,
                                                                          @RequestBody UpdateRestaurantJson updateRestaurantJson);

    @Operation(summary = "Exclui um restaurante pelo ID",
               description = "Exclui um restaurante. Não é possível excluir restaurantes que possuem itens de menu associados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurante excluído com sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão ou não é o proprietário do restaurante"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "409", description = "Não é possível excluir o restaurante pois possui itens de menu associados")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
        void delete(@RequestHeader("X-Requester-User-Id") UUID requesterUserId,
                                @PathVariable UUID id);
}
