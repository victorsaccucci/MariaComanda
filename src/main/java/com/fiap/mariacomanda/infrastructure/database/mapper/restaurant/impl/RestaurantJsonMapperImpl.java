package com.fiap.mariacomanda.infrastructure.database.mapper.restaurant.impl;

import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.input.UpdateRestaurantInputDTO;
import com.fiap.mariacomanda.infrastructure.database.mapper.restaurant.RestaurantJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.CreateRestaurantJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateRestaurantJson;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RestaurantJsonMapperImpl implements RestaurantJsonMapper {

    @Override
    public CreateRestaurantInputDTO toCreateInput(CreateRestaurantJson json) {
        return new CreateRestaurantInputDTO(
                json.getName(),
                json.getAddress(),
                json.getCuisineType(),
                json.getOpeningHours(),
                json.getOwnerUserId()
        );
    }

    @Override
    public UpdateRestaurantInputDTO toUpdateInput(UUID id, UpdateRestaurantJson json) {
        return new UpdateRestaurantInputDTO(
                id,
                json.getName(),
                json.getAddress(),
                json.getCuisineType(),
                json.getOpeningHours(),
                json.getOwnerUserId()
        );
    }
}
