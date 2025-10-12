package com.fiap.mariacomanda.core.mapper;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.output.CreateMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.output.GetMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.input.UpdateMenuItemInputDTO;

import java.util.List;

public interface MenuItemMapper {

    // Create
    MenuItem toDomain(CreateMenuItemInputDTO dto);
    CreateMenuItemOutputDTO toCreateOutput(MenuItem menuItem);

    // Get
    GetMenuItemOutputDTO toGetOutput(MenuItem menuItem);
    List<GetMenuItemOutputDTO> toGetOutputList(List<MenuItem> menuItems);

    // Update
    MenuItem toDomain(UpdateMenuItemInputDTO dto);
}
