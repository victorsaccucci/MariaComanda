package com.fiap.mariacomanda.core.mapper;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.input.UpdateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.CreateRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.GetRestaurantOutputDTO;

import java.util.List;

public interface RestaurantMapper {

    // Create
    Restaurant toDomain(CreateRestaurantInputDTO dto);
    CreateRestaurantOutputDTO toCreateOutput(Restaurant restaurant);

    // Get
    GetRestaurantOutputDTO toGetOutput(Restaurant restaurant);
    List<GetRestaurantOutputDTO> toGetOutputList(List<Restaurant> restaurants);

    // Update
    Restaurant toDomain(UpdateRestaurantInputDTO dto);
}
