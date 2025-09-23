package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.domain.usecases.restaurant.DeleteRestaurantUseCase;

import java.util.UUID;

public class DeleteRestaurantUseCaseImpl implements DeleteRestaurantUseCase {
    private final RestaurantGateway repository;

    public DeleteRestaurantUseCaseImpl(RestaurantGateway repository) {
        this.repository = repository;
    }

    @Override
    public void execute(UUID id) {
        repository.deleteById(id);
    }
}
