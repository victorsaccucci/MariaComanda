package com.fiap.mariacomanda.core.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.dto.menuItem.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.core.dto.menuItem.CreateMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuItem.GetMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuItem.UpdateMenuItemInputDTO;

public class MenuItemMapperImpl implements MenuItemMapper {

    @Override
    public MenuItem mapCreateInputToDomain(CreateMenuItemInputDTO dto){
        return new MenuItem(
            dto.getId(),
            dto.getRestaurantId(),
            dto.getName(),
            dto.getDescription(),
            dto.getPrice(),
            dto.getDineInOnly(),
            dto.getPhotoPath()
        );
    }

    @Override
    public CreateMenuItemOutputDTO mapCreateDomainToOutput(MenuItem menuItem){
        return new CreateMenuItemOutputDTO(
            menuItem.id(),
            menuItem.restaurantId(),
            menuItem.name(),
            menuItem.description(),
            menuItem.price(),
            menuItem.dineInOnly(),
            menuItem.photoPath()
        );
    }

    @Override
    public GetMenuItemOutputDTO mapGetDomainToOutput(MenuItem menuItem){
        return new GetMenuItemOutputDTO(
            menuItem.id(),
            menuItem.restaurantId(),
            menuItem.name(),
            menuItem.description(),
            menuItem.price(),
            menuItem.dineInOnly(),
            menuItem.photoPath()
        );
    }

    @Override
    public List<GetMenuItemOutputDTO> mapGetMenuItemOutputDtoToList(List<MenuItem> menuItens){
        return menuItens.stream().map(this::mapGetDomainToOutput).collect(Collectors.toList());
    }

    @Override
    public MenuItem mapUpdateInputToDomain(UpdateMenuItemInputDTO dto){
        return new MenuItem(
            dto.getId(),
            dto.getRestaurantId(),
            dto.getName(),
            dto.getDescription(),
            dto.getPrice(),
            dto.getDineInOnly(),
            dto.getPhotoPath()
        );
    }

}
