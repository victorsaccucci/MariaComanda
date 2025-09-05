package com.fiap.mariacomanda.core.usecases.restaurant;

import com.fiap.mariacomanda.core.gateway.RestaurantGateway;

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
