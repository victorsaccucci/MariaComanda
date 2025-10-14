package com.fiap.mariacomanda.core.domain.usecases.common;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.exception.ValidationException;

import java.util.UUID;

/**
 * Helper para validações de restaurante.
 * Métodos estáticos, sem dependências externas.
 */
public final class RestaurantValidator {

    private RestaurantValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static void validateRestaurantId(UUID restaurantId) {
        if (restaurantId == null) {
            throw new ValidationException("Restaurant ID cannot be null");
        }
    }

    public static void validateUserOwnsRestaurant(Restaurant restaurant, UUID ownerUserId) {
        // Substituir exceptions para especificos de validação de restaurant
        if (restaurant == null) {
            throw new ValidationException("Restaurant cannot be null");
        }
        if (ownerUserId == null) {
            throw new ValidationException("Owner user ID cannot be null");
        }

        if (!restaurant.getOwnerUserId().equals(ownerUserId)) {
            throw new ValidationException("Requester does not own the restaurant");
        }
    }
}
