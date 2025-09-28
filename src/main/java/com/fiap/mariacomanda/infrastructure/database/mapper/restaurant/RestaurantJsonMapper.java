package com.fiap.mariacomanda.infrastructure.database.mapper.restaurant;

import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.RestaurantJson;
import org.springframework.stereotype.Component;

@Component
public class RestaurantJsonMapper {

    public CreateRestaurantInputDTO map(RestaurantJson json) {
        return new CreateRestaurantInputDTO(
                json.getId(),
                json.getName(),
                json.getAddress(),
                json.getCuisineType(),
                json.getOpeningHours(),
                json.getOwnerUserId()
        );
    }
}
