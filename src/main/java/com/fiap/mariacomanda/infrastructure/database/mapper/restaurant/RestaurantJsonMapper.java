package com.fiap.mariacomanda.infrastructure.database.mapper.restaurant;

import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.RestaurantJson;

public interface RestaurantJsonMapper {

    CreateRestaurantInputDTO toCreateInput(RestaurantJson json);
}
