package com.fiap.mariacomanda.core.usecases.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.gateway.RestaurantGateway;

public class UpdateRestaurantUseCaseImpl implements UpdateRestaurantUseCase {
    private final RestaurantGateway repository;

    public UpdateRestaurantUseCaseImpl(RestaurantGateway repository) {
        this.repository = repository;
    }

    @Override
    public Restaurant execute(Restaurant restaurant) {
        if (restaurant.id() == null) throw new IllegalArgumentException("id is required");
        return repository.save(restaurant);
    }
}