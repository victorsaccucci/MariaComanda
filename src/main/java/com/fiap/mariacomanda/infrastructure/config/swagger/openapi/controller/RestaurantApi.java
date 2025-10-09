package com.fiap.mariacomanda.infrastructure.config.swagger.openapi.controller;

import com.fiap.mariacomanda.core.dto.restaurant.input.UpdateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.CreateRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.GetRestaurantOutputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.RestaurantJson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Restaurants", description = "Operações relacionada ao restaurante")
@RequestMapping("/api/restaurants")
public interface RestaurantApi {

    @Operation(summary = "Cria um novo restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso")
    })
    @PostMapping
    CreateRestaurantOutputDTO create(@RequestHeader("X-Requester-User-Id") UUID requesterUserId, @RequestBody RestaurantJson restaurantJson);

    @Operation(summary = "Busca um restaurante pelo nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado")
    })
    @GetMapping("/{name}")
    GetRestaurantOutputDTO get(@PathVariable String name);

    @Operation(summary = "Lista os restaurantes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de restaurantes retornada com sucesso")
    })
    @GetMapping
    List<GetRestaurantOutputDTO> list(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "20") int size);

    @Operation(summary = "Atualiza os dados de um restaurante pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso")
    })
        @PutMapping("/{id}")
        GetRestaurantOutputDTO update(@RequestHeader("X-Requester-User-Id") UUID requesterUserId,
                                                                  @PathVariable UUID id,
                                                                  @RequestBody UpdateRestaurantInputDTO dto);

    @Operation(summary = "Exclui um restaurante pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurante excluído com sucesso")
    })
    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id);
}
