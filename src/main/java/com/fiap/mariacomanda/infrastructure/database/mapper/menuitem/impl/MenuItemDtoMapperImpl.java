package com.fiap.mariacomanda.infrastructure.database.mapper.menuitem.impl;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.input.UpdateMenuItemInputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.output.CreateMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.output.GetMenuItemOutputDTO;
import com.fiap.mariacomanda.infrastructure.database.mapper.menuitem.MenuItemDtoMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuItemDtoMapperImpl implements MenuItemDtoMapper {

    @Override
    public MenuItem toDomain(CreateMenuItemInputDTO dto) {
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
    public MenuItem toDomain(UpdateMenuItemInputDTO dto) {
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
    public CreateMenuItemOutputDTO toCreateOutput(MenuItem menuItem) {
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
    public GetMenuItemOutputDTO toGetOutput(MenuItem menuItem) {
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
    public List<GetMenuItemOutputDTO> toGetOutputList(List<MenuItem> menuItems) {
        return menuItems.stream().map(this::toGetOutput).collect(Collectors.toList());
    }
}
