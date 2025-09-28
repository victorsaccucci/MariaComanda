package com.fiap.mariacomanda.infrastructure.database.mapper.menuitem;

import com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateMenuItemJson;

public interface MenuItemJsonMapper {

    CreateMenuItemInputDTO toCreateInput(CreateMenuItemJson json);
}
