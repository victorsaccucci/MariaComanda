package com.fiap.mariacomanda.core.controller;

import java.util.List;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.dto.menuItem.*;
import com.fiap.mariacomanda.core.mapper.MenuItemMapper;
import com.fiap.mariacomanda.core.usecases.menuItem.*;

public class MenuItemController {

    private final CreateMenuItemUseCase create;
    private final DeleteMenuItemUseCase delete;
    private final GetMenuItemUseCase get;
    private final ListMenuItemUseCase list;
    private final UpdateMenuItemUseCase update;
    private final MenuItemMapper menuItemMapper;

    public MenuItemController(CreateMenuItemUseCase create, DeleteMenuItemUseCase delete, GetMenuItemUseCase get,
            ListMenuItemUseCase list, UpdateMenuItemUseCase update, MenuItemMapper menuItemMapper) {
        this.create = create;
        this.delete = delete;
        this.get = get;
        this.list = list;
        this.update = update;
        this.menuItemMapper = menuItemMapper;
    }

    public CreateMenuItemOutputDTO create(CreateMenuItemInputDTO inputDTO) {
        MenuItem menuItem = menuItemMapper.mapCreateInputToDomain(inputDTO);
        MenuItem created = create.execute(menuItem);
        return menuItemMapper.mapCreateDomainToOutput(created);
    }

    public void delete(DeleteMenuItemInputDTO inputDTO){
        delete.execute(inputDTO.getId());
    }

    public GetMenuItemOutputDTO get(GetMenuItemInputDTO inputDTO) {
        MenuItem menuItem = get.execute(inputDTO.getId())
        .orElseThrow(() -> new RuntimeException("Menu item not found"));
        return menuItemMapper.mapGetDomainToOutput(menuItem);

    }

    public List<GetMenuItemOutputDTO> list(ListMenuItemInputDTO inputDTO) {
        List<MenuItem> menuItens = list.execute(inputDTO.getRestaurantId(), inputDTO.getPage(), inputDTO.getSize());
        return menuItemMapper.mapGetMenuItemOutputDtoToList(menuItens);
    }

    public GetMenuItemOutputDTO update(UpdateMenuItemInputDTO inputDTO) {
        MenuItem menuItem = menuItemMapper.mapUpdateInputToDomain(inputDTO);
        MenuItem updated = update.execute(menuItem);
        return menuItemMapper.mapGetDomainToOutput(updated);
    }

}
