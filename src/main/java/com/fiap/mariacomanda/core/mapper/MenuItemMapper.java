package com.fiap.mariacomanda.core.mapper;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.dto.menuItem.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.core.dto.menuItem.CreateMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuItem.GetMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuItem.UpdateMenuItemInputDTO;

import java.util.List;

public interface MenuItemMapper {

    MenuItem mapCreateInputToDomain(CreateMenuItemInputDTO dto);

    CreateMenuItemOutputDTO mapCreateDomainToOutput(MenuItem menuItem);

    GetMenuItemOutputDTO mapGetDomainToOutput(MenuItem menuItem);

    List<GetMenuItemOutputDTO> mapGetMenuItemOutputDtoToList(List<MenuItem> menuItens);

    MenuItem mapUpdateInputToDomain(UpdateMenuItemInputDTO dto);
}
