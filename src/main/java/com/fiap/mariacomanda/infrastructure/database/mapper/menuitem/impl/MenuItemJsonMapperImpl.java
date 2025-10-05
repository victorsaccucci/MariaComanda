package com.fiap.mariacomanda.infrastructure.database.mapper.menuitem.impl;

import com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.infrastructure.database.mapper.menuitem.MenuItemJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.CreateMenuItemJson;
import org.springframework.stereotype.Component;

@Component
public class MenuItemJsonMapperImpl implements MenuItemJsonMapper {

    @Override
    public CreateMenuItemInputDTO toCreateInput(CreateMenuItemJson json) {
        return new CreateMenuItemInputDTO(
                json.getName(),
                json.getDescription(),
                json.getPrice(),
                json.getRestaurantId(),
                json.isDineInOnly(),
                json.getPhotoPath()
        );
    }
}
