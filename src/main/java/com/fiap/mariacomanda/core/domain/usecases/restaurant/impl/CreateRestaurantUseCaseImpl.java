package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.domain.usecases.restaurant.CreateRestaurantUseCase;


public class CreateRestaurantUseCaseImpl implements CreateRestaurantUseCase {
    private final RestaurantGateway repository;

    public CreateRestaurantUseCaseImpl(RestaurantGateway repository) {
        this.repository = repository;
    }

    @Override
    public Restaurant execute(Restaurant newRestaurant) {
        return repository.save(newRestaurant);
    }
}