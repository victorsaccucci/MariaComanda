package com.fiap.mariacomanda.core.usecases.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;

public interface UpdateRestaurantUseCase {
    Restaurant execute(Restaurant restaurant);
}