package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.usecases.menuItem.UpdateMenuItemUseCase;
import com.fiap.mariacomanda.core.dto.menuitem.input.UpdateMenuItemInputDTO;

import java.math.BigDecimal;
import java.util.UUID;

public class UpdateMenuItemUseCaseImpl implements UpdateMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;
    private final RestaurantGateway restaurantGateway;

    public UpdateMenuItemUseCaseImpl(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway) {
        this.menuItemGateway = menuItemGateway;
        this.restaurantGateway = restaurantGateway;
    }

    @Override
    public MenuItem execute(UpdateMenuItemInputDTO inputDTO) {
        if (inputDTO == null) {
            throw new IllegalArgumentException("UpdateMenuItemInputDTO cannot be null");
        }
        if (inputDTO.id() == null) {
            throw new IllegalArgumentException("MenuItem id is required");
        }

        MenuItem existing = menuItemGateway.findById(inputDTO.id())
                .orElseThrow(() -> new IllegalArgumentException("MenuItem not found"));

        // Verificar se o restaurantId foi passado e validar
        UUID restaurantId = inputDTO.restaurantId() != null ? inputDTO.restaurantId() : existing.getRestaurantId();
        resolveRestaurant(restaurantId);

        // validar requester id

        // menuItem com dados atualizados
        MenuItem merged = new MenuItem(
            existing.getId(),
            restaurantId,
            updateValue(inputDTO.name(), existing.getName()),
            updateValue(inputDTO.description(), existing.getDescription()),
            updateValue(inputDTO.price(), existing.getPrice()),
            inputDTO.dineInOnly(),
            updateValue(inputDTO.photoPath(), existing.getPhotoPath())
        );

        return menuItemGateway.save(merged);
    }

    private Restaurant resolveRestaurant(UUID restaurantId) {
        if (restaurantId == null) {
            throw new IllegalArgumentException("Restaurant ID cannot be null");
        }

        return restaurantGateway.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
    }

    private String updateValue(String newValue, String current) {
        return newValue != null ? newValue : current;
    }

    private BigDecimal updateValue(BigDecimal newValue, BigDecimal current) {
        return newValue != null ? newValue : current;
    }
}
