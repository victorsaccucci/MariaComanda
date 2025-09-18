package com.fiap.mariacomanda.infrastructure.database.mapper;

import org.springframework.stereotype.Component;

import com.fiap.mariacomanda.core.dto.menuItem.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateMenuItemJson;

@Component
public class MenuItemJsonMapper {

    public CreateMenuItemInputDTO mapJsonToInputDTO(CreateMenuItemJson json) {
        return new CreateMenuItemInputDTO(
            json.getId(),
            json.getRestaurantId(),
            json.getName(),
            json.getDescription(),
            json.getPrice(),
            json.isDineInOnly(),
            json.getPhotoPath()
        );
    }
}
