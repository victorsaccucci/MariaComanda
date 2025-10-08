package com.fiap.mariacomanda.core.domain.usecases.common;

import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;

import java.util.UUID;

/**
 * Validador para regras específicas de restaurante.
 * Responsável por validar propriedade e outras regras de negócio relacionadas a restaurantes.
 * Segue o princípio da responsabilidade única.
 */
public class RestaurantValidator {

    private final RestaurantGateway restaurantGateway;

    public RestaurantValidator(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    /**
     * Busca o restaurante e valida se foi encontrado.
     *
     * @param restaurantId ID do restaurante
     * @return Restaurante encontrado
     * @throws IllegalArgumentException se o ID for null ou restaurante não encontrado
     */
    public Restaurant findRestaurant(UUID restaurantId) {
        if (restaurantId == null) {
            throw new IllegalArgumentException("Restaurant ID cannot be null");
        }

        return restaurantGateway.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
    }

    /**
     * Valida se o restaurante pertence ao usuário especificado.
     *
     * @param restaurant Restaurante a ser validado
     * @param ownerUserId ID do usuário que deveria ser o proprietário
     * @throws IllegalStateException se o restaurante não pertencer ao usuário
     */
    public void validateUserOwnsRestaurant(Restaurant restaurant, UUID ownerUserId) {
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant cannot be null");
        }
        if (ownerUserId == null) {
            throw new IllegalArgumentException("Owner user ID cannot be null");
        }

        if (!restaurant.getOwnerUserId().equals(ownerUserId)) {
            throw new IllegalStateException("Requester does not own the restaurant");
        }
    }
}
