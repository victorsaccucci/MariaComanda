package com.fiap.mariacomanda.infrastructure.database.mapper.menuitem;

import com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.input.UpdateMenuItemInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateMenuItemJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateMenuItemJson;

import java.util.UUID;

public interface MenuItemJsonMapper {

    CreateMenuItemInputDTO toCreateInput(CreateMenuItemJson json);

    UpdateMenuItemInputDTO toUpdateInput(UUID id, UpdateMenuItemJson json);
}
