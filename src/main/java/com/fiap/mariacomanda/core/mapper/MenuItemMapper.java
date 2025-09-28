package com.fiap.mariacomanda.core.mapper;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.output.CreateMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.output.GetMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.input.UpdateMenuItemInputDTO;

import java.util.List;

public interface MenuItemMapper {

    MenuItem mapCreateInputToDomain(CreateMenuItemInputDTO dto);

    CreateMenuItemOutputDTO mapCreateDomainToOutput(MenuItem menuItem);

    GetMenuItemOutputDTO mapGetDomainToOutput(MenuItem menuItem);

    List<GetMenuItemOutputDTO> mapGetMenuItemOutputDtoToList(List<MenuItem> menuItens);

    MenuItem mapUpdateInputToDomain(UpdateMenuItemInputDTO dto);
}
