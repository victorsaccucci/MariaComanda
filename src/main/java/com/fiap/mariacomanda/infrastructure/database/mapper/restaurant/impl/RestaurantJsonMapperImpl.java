package com.fiap.mariacomanda.infrastructure.database.mapper.restaurant.impl;

import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.infrastructure.database.mapper.restaurant.RestaurantJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.RestaurantJson;
import org.springframework.stereotype.Component;

@Component
public class RestaurantJsonMapperImpl implements RestaurantJsonMapper {

    @Override
    public CreateRestaurantInputDTO toCreateInput(RestaurantJson json) {
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
