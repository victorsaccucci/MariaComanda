package com.fiap.mariacomanda.core.domain.usecases.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.dto.restaurant.input.UpdateRestaurantInputDTO;

public interface UpdateRestaurantUseCase {
    Restaurant execute(UpdateRestaurantInputDTO inputDTO);
}