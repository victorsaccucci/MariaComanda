package com.fiap.mariacomanda.core.usecases.restaurant;

import com.fiap.mariacomanda.core.gateway.RestaurantGateway;

import java.util.UUID;

public class DeleteRestaurantUseCaseImpl implements DeleteRestaurantUseCase {
    private final RestaurantGateway gateway;

    public DeleteRestaurantUseCaseImpl(RestaurantGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(UUID id) {
        gateway.deleteById(id);
    }
}
