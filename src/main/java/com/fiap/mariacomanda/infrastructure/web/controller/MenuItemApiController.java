package com.fiap.mariacomanda.infrastructure.web.controller;

import com.fiap.mariacomanda.core.controller.MenuItemController;
import com.fiap.mariacomanda.core.dto.menuItem.*;
import com.fiap.mariacomanda.infrastructure.config.swagger.openapi.controller.MenuItemApi;
import com.fiap.mariacomanda.infrastructure.database.mapper.MenuItemJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.CreateMenuItemJson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MenuItemApiController implements MenuItemApi {

    private final MenuItemController menuItemController;

    private final MenuItemJsonMapper MapperJson;

    public CreateMenuItemOutputDTO create(@Valid @RequestBody CreateMenuItemJson createMenuItemJson) {
        var inputDTO = MapperJson.mapJsonToInputDTO(createMenuItemJson);
        return menuItemController.create(inputDTO);
    }

    public void delete(@PathVariable UUID id) {
        var inputDTO = new DeleteMenuItemInputDTO(id);
        menuItemController.delete(inputDTO);
    }

    public GetMenuItemOutputDTO get(@PathVariable UUID id) {
        var inputDTO = new GetMenuItemInputDTO(id, id, null, null, null, false, null);
        return menuItemController.get(inputDTO);
    }

    public List<GetMenuItemOutputDTO> list(
            @RequestParam UUID restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        var inputDTO = new ListMenuItemInputDTO(restaurantId, page, size);
        return menuItemController.list(inputDTO);
    }

    public GetMenuItemOutputDTO update(@PathVariable UUID id, @RequestBody UpdateMenuItemInputDTO dto) {
        var inputDTO = new UpdateMenuItemInputDTO(id, dto.getRestaurantId(), dto.getName(), dto.getDescription(), dto.getPrice(), dto.getDineInOnly(), dto.getPhotoPath());
        return menuItemController.update(inputDTO);
    }

}