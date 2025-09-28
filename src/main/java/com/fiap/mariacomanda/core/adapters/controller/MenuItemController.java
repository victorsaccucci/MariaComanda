package com.fiap.mariacomanda.core.adapters.controller;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.domain.usecases.menuItem.*;
import com.fiap.mariacomanda.core.dto.menuitem.input.*;
import com.fiap.mariacomanda.core.dto.menuitem.output.CreateMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.output.GetMenuItemOutputDTO;
import com.fiap.mariacomanda.core.mapper.MenuItemMapper;

import java.util.List;
import java.util.UUID;

public class MenuItemController {

    private final CreateMenuItemUseCase createUseCase;
    private final DeleteMenuItemUseCase deleteUseCase;
    private final GetMenuItemUseCase getUseCase;
    private final ListMenuItemUseCase listUseCase;
    private final UpdateMenuItemUseCase updateUseCase;
    private final MenuItemMapper menuItemMapper;

    public MenuItemController(CreateMenuItemUseCase createUseCase, DeleteMenuItemUseCase deleteUseCase, GetMenuItemUseCase getUseCase,
                              ListMenuItemUseCase listUseCase, UpdateMenuItemUseCase updateUseCase, MenuItemMapper menuItemMapper) {
        this.createUseCase = createUseCase;
        this.deleteUseCase = deleteUseCase;
        this.getUseCase = getUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.menuItemMapper = menuItemMapper;
    }

    public CreateMenuItemOutputDTO create(CreateMenuItemInputDTO inputDTO) {
        MenuItem menuItem = menuItemMapper.mapCreateInputToDomain(inputDTO);
        MenuItem created = createUseCase.execute(menuItem);
        return menuItemMapper.mapCreateDomainToOutput(created);
    }

    public void delete(DeleteMenuItemInputDTO inputDTO) {
        UUID id = inputDTO.id();
        deleteUseCase.execute(id);
    }

    public GetMenuItemOutputDTO get(GetMenuItemInputDTO inputDTO) {
        MenuItem menuItem = getUseCase.execute(inputDTO.id()).orElseThrow();
        return menuItemMapper.mapGetDomainToOutput(menuItem);
    }

    public List<GetMenuItemOutputDTO> list(ListMenuItemInputDTO inputDTO) {
        List<MenuItem> menuItems = listUseCase.execute(inputDTO.restaurantId(), inputDTO.page(), inputDTO.size());
        return menuItemMapper.mapGetMenuItemOutputDtoToList(menuItems);
    }

    public GetMenuItemOutputDTO update(UpdateMenuItemInputDTO inputDTO) {
        MenuItem menuItem = menuItemMapper.mapUpdateInputToDomain(inputDTO);
        MenuItem updated = updateUseCase.execute(menuItem);
        return menuItemMapper.mapGetDomainToOutput(updated);
    }
}
