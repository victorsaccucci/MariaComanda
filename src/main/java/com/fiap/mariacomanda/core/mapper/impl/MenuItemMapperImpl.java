package com.fiap.mariacomanda.core.mapper.impl;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.output.CreateMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.output.GetMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.input.UpdateMenuItemInputDTO;
import com.fiap.mariacomanda.core.mapper.MenuItemMapper;

import java.util.List;
import java.util.stream.Collectors;

public class MenuItemMapperImpl implements MenuItemMapper {

    @Override
    public MenuItem mapCreateInputToDomain(CreateMenuItemInputDTO dto) {
        return new MenuItem(
                dto.id(),
                dto.restaurantId(),
                dto.name(),
                dto.description(),
                dto.price(),
                dto.dineInOnly(),
                dto.photoPath()
        );
    }

    @Override
    public CreateMenuItemOutputDTO mapCreateDomainToOutput(MenuItem menuItem) {
        return new CreateMenuItemOutputDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getRestaurantId(),
                menuItem.isDineInOnly(),
                menuItem.getPhotoPath()
        );
    }

    @Override
    public GetMenuItemOutputDTO mapGetDomainToOutput(MenuItem menuItem) {
        return new GetMenuItemOutputDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getRestaurantId(),
                menuItem.isDineInOnly(),
                menuItem.getPhotoPath()
        );
    }

    @Override
    public List<GetMenuItemOutputDTO> mapGetMenuItemOutputDtoToList(List<MenuItem> menuItens) {
        return menuItens.stream().map(this::mapGetDomainToOutput).collect(Collectors.toList());
    }

    @Override
    public MenuItem mapUpdateInputToDomain(UpdateMenuItemInputDTO dto) {
        return new MenuItem(
                dto.id(),
                dto.restaurantId(),
                dto.name(),
                dto.description(),
                dto.price(),
                dto.dineInOnly(),
                dto.photoPath()
        );
    }

}
