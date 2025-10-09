package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.usecases.common.AuthorizationValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.NullObjectValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.RestaurantValidator;
import com.fiap.mariacomanda.core.domain.usecases.menuItem.UpdateMenuItemUseCase;
import com.fiap.mariacomanda.core.dto.menuitem.input.UpdateMenuItemInputDTO;

import java.math.BigDecimal;
import java.util.UUID;

public class UpdateMenuItemUseCaseImpl implements UpdateMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;
    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;

    public UpdateMenuItemUseCaseImpl(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway,
                                     UserGateway userGateway) {
        this.menuItemGateway = menuItemGateway;
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
    }

    @Override
    public MenuItem execute(UpdateMenuItemInputDTO inputDTO, UUID requesterUserId) {
        NullObjectValidator.validateNotNull(inputDTO, UpdateMenuItemInputDTO.class.getName());
        NullObjectValidator.validateNotNull(inputDTO.id(), "menuItemId");

        AuthorizationValidator.validateRequesterUserId(requesterUserId);
        User requester = userGateway.findById(requesterUserId)
                .orElseThrow(() -> new IllegalArgumentException("Requester user not found"));
        AuthorizationValidator.validateRequesterIsOwner(requester, "update menu items");

        MenuItem existing = menuItemGateway.findById(inputDTO.id())
                .orElseThrow(() -> new IllegalArgumentException("MenuItem not found"));

        Restaurant existingRestaurant = restaurantGateway.findById(existing.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found for menu item"));
        RestaurantValidator.validateUserOwnsRestaurant(existingRestaurant, requesterUserId);

        UUID restaurantId = inputDTO.restaurantId() != null ? inputDTO.restaurantId() : existing.getRestaurantId();
        Restaurant targetRestaurant = existingRestaurant;

        if (!existing.getRestaurantId().equals(restaurantId)) {
            RestaurantValidator.validateRestaurantId(restaurantId);
            targetRestaurant = restaurantGateway.findById(restaurantId)
                    .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
            RestaurantValidator.validateUserOwnsRestaurant(targetRestaurant, requesterUserId);
        }

        MenuItem merged = new MenuItem(
            existing.getId(),
            targetRestaurant.getId(),
            updateValue(inputDTO.name(), existing.getName()),
            updateValue(inputDTO.description(), existing.getDescription()),
            updateValue(inputDTO.price(), existing.getPrice()),
            inputDTO.dineInOnly(),
            updateValue(inputDTO.photoPath(), existing.getPhotoPath())
        );

        return menuItemGateway.save(merged);
    }

    private String updateValue(String newValue, String current) {
        return newValue != null ? newValue : current;
    }

    private BigDecimal updateValue(BigDecimal newValue, BigDecimal current) {
        return newValue != null ? newValue : current;
    }
}
