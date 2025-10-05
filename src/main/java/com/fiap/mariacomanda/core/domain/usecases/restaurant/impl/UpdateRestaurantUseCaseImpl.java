package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.usecases.restaurant.UpdateRestaurantUseCase;
import com.fiap.mariacomanda.core.dto.restaurant.input.UpdateRestaurantInputDTO;

import java.util.UUID;

public class UpdateRestaurantUseCaseImpl implements UpdateRestaurantUseCase {
    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;

    public UpdateRestaurantUseCaseImpl(RestaurantGateway restaurantGateway, UserGateway userGateway) {
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
    }

    @Override
    public Restaurant execute(UpdateRestaurantInputDTO inputDTO) {
        if (inputDTO == null) {
            throw new IllegalArgumentException("UpdateRestaurantInputDTO cannot be null");
        }
        if (inputDTO.id() == null) {
            throw new IllegalArgumentException("Restaurant id is required");
        }

        Restaurant existing = restaurantGateway.findById(inputDTO.id())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        // validar requesterId

        // Verificar se o ownerUserId foi passado e se é válido
        UUID ownerUserId = inputDTO.ownerUserId() != null ? inputDTO.ownerUserId() : existing.getOwnerUserId();
        User owner = resolveUser(ownerUserId);
        UserType ownerType = owner.getUserType();
        if (ownerType == null || !ownerType.isOwner()) {
            throw new IllegalStateException("Only OWNER users can own restaurants");
        }

        // restaurante com dados atualizados
        Restaurant merged = new Restaurant(
            existing.getId(),
            updateValue(inputDTO.name(), existing.getName()),
            updateValue(inputDTO.address(), existing.getAddress()),
            updateValue(inputDTO.cuisineType(), existing.getCuisineType()),
            updateValue(inputDTO.openingHours(), existing.getOpeningHours()),
            ownerUserId
        );

        return restaurantGateway.save(merged);
    }

    private User resolveUser(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        return userGateway.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + userId));
    }

    private String updateValue(String newValue, String current) {
        return newValue != null ? newValue : current;
    }
}