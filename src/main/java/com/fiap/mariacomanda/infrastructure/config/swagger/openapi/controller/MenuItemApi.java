package com.fiap.mariacomanda.infrastructure.config.swagger.openapi.controller;

import com.fiap.mariacomanda.core.dto.menuitem.output.CreateMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.output.GetMenuItemOutputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateMenuItemJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateMenuItemJson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/menu-item")
@Tag(name = "Menu Item", description = "Operações relacionadas aos itens do cardápio.")
public interface MenuItemApi {

    @PostMapping
    @Operation(summary = "Criar um novo prato no menu")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prato criado com sucesso")
    })
    CreateMenuItemOutputDTO create(@RequestHeader("X-Requester-User-Id") UUID requesterUserId, @Valid @RequestBody CreateMenuItemJson createMenuItemJson);

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um prato no menu pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Prato excluído com sucesso")
    })
    ResponseEntity<Void> delete(@RequestHeader("X-Requester-User-Id") UUID requesterUserId,
                                @PathVariable UUID id);

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um prato no menu pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prato encontrado")
    })
    GetMenuItemOutputDTO get(@PathVariable UUID id);

    @GetMapping
    @Operation(summary = "Listar os pratos do menu pelo restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pratos retornada")
    })
    List<GetMenuItemOutputDTO> list(
            @RequestParam UUID restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size);


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um prato do menu pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prato atualizado com sucesso")
    })
    GetMenuItemOutputDTO update(@RequestHeader("X-Requester-User-Id") UUID requesterUserId,
                                @PathVariable UUID id,
                                @RequestBody UpdateMenuItemJson updateMenuItemJson);
}
