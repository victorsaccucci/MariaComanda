package com.fiap.mariacomanda.core.usecases.restaurant;

import java.util.UUID;

public interface DeleteRestaurantUseCase {
    void execute(UUID id);
}