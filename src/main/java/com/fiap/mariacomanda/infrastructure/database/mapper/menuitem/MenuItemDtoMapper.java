package com.fiap.mariacomanda.infrastructure.database.mapper.menuitem;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.input.UpdateMenuItemInputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.output.CreateMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.output.GetMenuItemOutputDTO;

import java.util.List;

public interface MenuItemDtoMapper {

    MenuItem toDomain(CreateMenuItemInputDTO dto);

    MenuItem toDomain(UpdateMenuItemInputDTO dto);

    CreateMenuItemOutputDTO toCreateOutput(MenuItem menuItem);

    GetMenuItemOutputDTO toGetOutput(MenuItem menuItem);

    List<GetMenuItemOutputDTO> toGetOutputList(List<MenuItem> menuItems);
}
