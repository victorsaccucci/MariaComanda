package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.usecases.common.RequesterValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.NullObjectValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.RestaurantValidator;
import com.fiap.mariacomanda.core.domain.usecases.menuItem.DeleteMenuItemUseCase;

import java.util.UUID;

public class DeleteMenuItemUseCaseImpl implements DeleteMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;
    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;

    public DeleteMenuItemUseCaseImpl(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway,
                                UserGateway userGateway) {
        this.menuItemGateway = menuItemGateway;
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
    }

    @Override
    public void execute(UUID id, UUID requesterUserId) {
        NullObjectValidator.validateNotNull(id, "menuItemId");

        RequesterValidator.validateRequesterUserId(requesterUserId);
        User requester = userGateway.findById(requesterUserId)
                .orElseThrow(() -> new IllegalArgumentException("Requester user not found"));
        RequesterValidator.validateRequesterIsOwner(requester, "delete menu items");

        MenuItem existing = menuItemGateway.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("MenuItem not found for id: " + id));

        Restaurant restaurant = restaurantGateway.findById(existing.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found for menu item"));
        // só o próprio dono do restaurante pode deletar seu menuitem
        RestaurantValidator.validateUserOwnsRestaurant(restaurant, requesterUserId);

        menuItemGateway.deleteById(id);
    }
}
