package com.fiap.mariacomanda.core.usecases.restaurant;

import java.util.UUID;

public class DeleteRestaurantUseCaseImpl implements DeleteRestaurantUseCase {
    private final RestaurantRepositoryPort repository;

    public DeleteRestaurantUseCaseImpl(RestaurantRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void execute(UUID id) {
        repository.deleteById(id);
    }
}
