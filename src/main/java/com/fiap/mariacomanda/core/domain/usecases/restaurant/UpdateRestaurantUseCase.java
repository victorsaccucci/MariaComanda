package com.fiap.mariacomanda.core.domain.usecases.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;

public interface UpdateRestaurantUseCase {
    Restaurant execute(Restaurant restaurant);
}