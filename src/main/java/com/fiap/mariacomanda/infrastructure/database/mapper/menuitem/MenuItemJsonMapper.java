package com.fiap.mariacomanda.infrastructure.database.mapper.menuitem;

import com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateMenuItemJson;
import org.springframework.stereotype.Component;

@Component
public class MenuItemJsonMapper {

    public CreateMenuItemInputDTO mapJsonToInputDTO(CreateMenuItemJson json) {
        return new CreateMenuItemInputDTO(
                json.getId(),
                json.getName(),
                json.getDescription(),
                json.getPrice(),
                json.getRestaurantId(),
                json.isDineInOnly(),
                json.getPhotoPath()
        );
    }
}
