package com.fiap.mariacomanda.infrastructure.web.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.fiap.mariacomanda.core.controller.MenuItemController;
import com.fiap.mariacomanda.core.dto.menuItem.*;
import com.fiap.mariacomanda.infrastructure.database.mapper.MenuItemJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.CreateMenuItemJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateMenuItemJson;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/menu-item")
@RequiredArgsConstructor
public class MenuItemApiController {

    private final MenuItemController menuItemController;

    private final MenuItemJsonMapper MapperJson;

    @PostMapping
    public CreateMenuItemOutputDTO create(@Valid @RequestBody CreateMenuItemJson createMenuItemJson) {
        var inputDTO = MapperJson.mapJsonToInputDTO(createMenuItemJson);
        return menuItemController.create(inputDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        var inputDTO = new DeleteMenuItemInputDTO(id);
        menuItemController.delete(inputDTO);
    }

    @GetMapping("/{id}")
    public GetMenuItemOutputDTO get(@PathVariable UUID id) {
        var inputDTO = new GetMenuItemInputDTO(id, id, null, null, null, false, null);
        return menuItemController.get(inputDTO);
    }

    @GetMapping
    public List<GetMenuItemOutputDTO> list(
            @RequestParam UUID restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        var inputDTO = new ListMenuItemInputDTO(restaurantId, page, size);
        return menuItemController.list(inputDTO);
    }

    @PutMapping("/{id}")
    public GetMenuItemOutputDTO update(@PathVariable UUID id, @RequestBody UpdateMenuItemJson updateMenuItemJson){
        GetMenuItemOutputDTO existingDto = menuItemController.get(new GetMenuItemInputDTO(id, id, null, null, null, false, null));
        UUID restaurantId = existingDto.getRestaurantId();
        var inputDTO = new UpdateMenuItemInputDTO(id, restaurantId, updateMenuItemJson.getName(), updateMenuItemJson.getDescription(), updateMenuItemJson.getPrice(), updateMenuItemJson.isDineInOnly(), updateMenuItemJson.getPhotoPath());
        return menuItemController.update(inputDTO);
    }

}
