package com.fiap.mariacomanda.infrastructure.web.controller;

import com.fiap.mariacomanda.core.adapters.controller.MenuItemController;
import com.fiap.mariacomanda.core.dto.menuitem.input.*;
import com.fiap.mariacomanda.core.dto.menuitem.output.CreateMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.output.GetMenuItemOutputDTO;
import com.fiap.mariacomanda.infrastructure.config.swagger.openapi.controller.MenuItemApi;
import com.fiap.mariacomanda.infrastructure.database.mapper.menuitem.MenuItemJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.CreateMenuItemJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateMenuItemJson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MenuItemApiController implements MenuItemApi {

    private final MenuItemController menuItemController;

    private final MenuItemJsonMapper menuItemJsonMapper;

    public CreateMenuItemOutputDTO create(@RequestHeader("X-Requester-User-Id") UUID requesterUserId, @Valid @RequestBody CreateMenuItemJson createMenuItemJson) {
        CreateMenuItemInputDTO inputDTO = menuItemJsonMapper.toCreateInput(createMenuItemJson);
        return menuItemController.create(inputDTO, requesterUserId);
    }

    public void delete(@PathVariable UUID id) {
        DeleteMenuItemInputDTO inputDTO = new DeleteMenuItemInputDTO(id);
        menuItemController.delete(inputDTO);
    }

    public GetMenuItemOutputDTO get(@PathVariable UUID id) {
        GetMenuItemInputDTO inputDTO = new GetMenuItemInputDTO(id, null, null, null, null, false, null);
        return menuItemController.get(inputDTO);
    }

    @GetMapping
    public List<GetMenuItemOutputDTO> list(
            @RequestParam(required = false) UUID restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        ListMenuItemInputDTO inputDTO = new ListMenuItemInputDTO(restaurantId, page, size);
        return menuItemController.list(inputDTO);
    }

    @PutMapping("/{id}")
    public GetMenuItemOutputDTO update(@PathVariable UUID id, @RequestBody UpdateMenuItemJson updateMenuItemJson) {
        GetMenuItemOutputDTO existingDto = menuItemController.get(new GetMenuItemInputDTO(id, null, null, null, null, false, null));
        UUID restaurantId = existingDto.restaurantId();
        UpdateMenuItemInputDTO inputDTO = new UpdateMenuItemInputDTO(id, updateMenuItemJson.getName(), updateMenuItemJson.getDescription(), updateMenuItemJson.getPrice(), restaurantId, updateMenuItemJson.isDineInOnly(), updateMenuItemJson.getPhotoPath());
        return menuItemController.update(inputDTO);
    }

}