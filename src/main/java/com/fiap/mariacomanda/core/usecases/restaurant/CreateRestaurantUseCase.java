package com.fiap.mariacomanda.core.usecases.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;

public interface CreateRestaurantUseCase {
    Restaurant execute(Restaurant restaurant);
}