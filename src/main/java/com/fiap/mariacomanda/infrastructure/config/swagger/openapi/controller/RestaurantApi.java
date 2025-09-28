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

@Tag(name = "Restaurants", description = "API para gerenciamento de restaurantes")
@RequestMapping("/api/restaurants")
public interface RestaurantApi {

    @Operation(summary = "Cria um novo restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    CreateRestaurantOutputDTO create(@RequestBody RestaurantJson restaurantJson);

    @Operation(summary = "Busca um restaurante pelo nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    @GetMapping("/{name}")
    GetRestaurantOutputDTO get(@PathVariable String name);

    @Operation(summary = "Lista restaurantes com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de restaurantes retornada com sucesso")
    })
    @GetMapping
    List<GetRestaurantOutputDTO> list(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "20") int size);

    @Operation(summary = "Atualiza os dados de um restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    @PutMapping("/{id}")
    GetRestaurantOutputDTO update(@PathVariable UUID id, @RequestBody UpdateRestaurantInputDTO dto);

    @Operation(summary = "Exclui um restaurante pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurante excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id);
}
