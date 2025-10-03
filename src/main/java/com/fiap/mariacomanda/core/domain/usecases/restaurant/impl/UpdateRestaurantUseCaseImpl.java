package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.domain.usecases.restaurant.UpdateRestaurantUseCase;

public class UpdateRestaurantUseCaseImpl implements UpdateRestaurantUseCase {
    private final RestaurantGateway gateway;

    public UpdateRestaurantUseCaseImpl(RestaurantGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Restaurant execute(Restaurant restaurant) {
        if (restaurant.getId() == null) throw new IllegalArgumentException("id is required");
        return gateway.save(restaurant);
    }
}