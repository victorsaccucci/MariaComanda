package com.fiap.mariacomanda.infrastructure.database.mapper.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.input.UpdateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.CreateRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.GetRestaurantOutputDTO;

import java.util.List;

public interface RestaurantDtoMapper {

    Restaurant toDomain(CreateRestaurantInputDTO dto);

    Restaurant toDomain(UpdateRestaurantInputDTO dto);

    CreateRestaurantOutputDTO toCreateOutput(Restaurant restaurant);

    GetRestaurantOutputDTO toGetOutput(Restaurant restaurant);

    List<GetRestaurantOutputDTO> toGetOutputList(List<Restaurant> restaurants);
}
