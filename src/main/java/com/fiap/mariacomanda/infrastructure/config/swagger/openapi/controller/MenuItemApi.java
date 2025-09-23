package com.fiap.mariacomanda.infrastructure.config.swagger.openapi.controller;

import com.fiap.mariacomanda.core.dto.menuItem.CreateMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuItem.GetMenuItemOutputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateMenuItemJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateMenuItemJson;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/menu-item")
@Tag(name = "Menu Items", description = "Operações relacionadas aos itens do cardápio.")
public interface MenuItemApi {


    @PostMapping
    @Operation(summary = "Criar um novo item de menu")
    CreateMenuItemOutputDTO create(@Valid @RequestBody CreateMenuItemJson createMenuItemJson);

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um item de menu pelo ID")
    void delete(@PathVariable UUID id);

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um item de menu pelo ID")
    GetMenuItemOutputDTO get(@PathVariable UUID id);

    @GetMapping
    @Operation(summary = "Listar itens de menu por restaurante")
    List<GetMenuItemOutputDTO> list(
            @RequestParam UUID restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size);

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um item de menu pelo ID")
    GetMenuItemOutputDTO update(@PathVariable UUID id, @RequestBody UpdateMenuItemJson updateMenuItemJson);
}
