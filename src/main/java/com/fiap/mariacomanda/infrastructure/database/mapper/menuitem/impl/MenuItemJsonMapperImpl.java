package com.fiap.mariacomanda.infrastructure.database.mapper.menuitem.impl;

import com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.input.UpdateMenuItemInputDTO;
import com.fiap.mariacomanda.infrastructure.database.mapper.menuitem.MenuItemJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.CreateMenuItemJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateMenuItemJson;

import org.springframework.stereotype.Component;

import java.util.UUID;

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

    @Override
    public UpdateMenuItemInputDTO toUpdateInput(UUID id, UpdateMenuItemJson json) {
        return new UpdateMenuItemInputDTO(
                id,
                json.getName(),
                json.getDescription(),
                json.getPrice(),
                json.getRestaurantId(),
                json.isDineInOnly(),
                json.getPhotoPath()
        );
    }
}
