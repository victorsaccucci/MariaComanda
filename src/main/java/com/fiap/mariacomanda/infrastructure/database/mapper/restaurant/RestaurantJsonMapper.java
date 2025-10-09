package com.fiap.mariacomanda.infrastructure.database.mapper.restaurant;

import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.input.UpdateRestaurantInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateRestaurantJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateRestaurantJson;

import java.util.UUID;

public interface RestaurantJsonMapper {

    CreateRestaurantInputDTO toCreateInput(CreateRestaurantJson json);

    UpdateRestaurantInputDTO toUpdateInput(UUID id, UpdateRestaurantJson json);
}
