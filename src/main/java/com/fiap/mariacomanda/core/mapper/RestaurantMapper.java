package com.fiap.mariacomanda.core.mapper;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.input.UpdateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.CreateRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.GetRestaurantOutputDTO;

import java.util.List;

public interface RestaurantMapper {

    Restaurant mapCreate(CreateRestaurantInputDTO dto);

    Restaurant mapUpdate(UpdateRestaurantInputDTO dto);

    CreateRestaurantOutputDTO mapCreate(Restaurant restaurant);

    GetRestaurantOutputDTO mapToGetOutputDTO(Restaurant restaurant);

    List<GetRestaurantOutputDTO> mapToGetOutputDTOList(List<Restaurant> restaurants);

}
