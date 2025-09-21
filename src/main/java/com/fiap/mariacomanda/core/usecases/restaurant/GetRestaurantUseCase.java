package com.fiap.mariacomanda.core.usecases.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;

import java.util.Optional;
import java.util.UUID;

public interface GetRestaurantUseCase {
    Optional<Restaurant> execute(UUID id);
}