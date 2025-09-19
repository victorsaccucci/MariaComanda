package com.fiap.mariacomanda.infrastructure.web.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.mariacomanda.core.controller.MenuItemController;
import com.fiap.mariacomanda.core.dto.menuItem.CreateMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuItem.DeleteMenuItemInputDTO;
import com.fiap.mariacomanda.core.dto.menuItem.GetMenuItemInputDTO;
import com.fiap.mariacomanda.core.dto.menuItem.GetMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuItem.ListMenuItemInputDTO;
import com.fiap.mariacomanda.core.dto.menuItem.UpdateMenuItemInputDTO;
import com.fiap.mariacomanda.infrastructure.database.mapper.MenuItemJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.CreateMenuItemJson;

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
    public GetMenuItemOutputDTO update(@PathVariable UUID id, @RequestBody UpdateMenuItemInputDTO dto){
        var inputDTO = new UpdateMenuItemInputDTO(id, dto.getRestaurantId(), dto.getName(), dto.getDescription(), dto.getPrice(), dto.getDineInOnly(), dto.getPhotoPath());
        return menuItemController.update(inputDTO);
    }

}
