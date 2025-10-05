package com.fiap.mariacomanda.core.domain.usecases.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;

import java.util.UUID;

public interface CreateRestaurantUseCase {
    Restaurant execute(CreateRestaurantInputDTO inputDTO, UUID requesterUserId);
}