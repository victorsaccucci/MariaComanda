package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.domain.exception.EntityNotFoundException;
import com.fiap.mariacomanda.core.domain.usecases.menuItem.ListMenuItemUseCase;

import java.util.List;
import java.util.UUID;

public class ListMenuItemUseCaseImpl implements ListMenuItemUseCase {

    private final MenuItemGateway gateway;
    private final RestaurantGateway restaurantGateway;

    public ListMenuItemUseCaseImpl(MenuItemGateway gateway, RestaurantGateway restaurantGateway) {
        this.gateway = gateway;
        this.restaurantGateway = restaurantGateway;
    }

    @Override
    public List<MenuItem> execute(UUID restaurantId, int page, int size) {
        if (restaurantId != null && restaurantGateway.findById(restaurantId).isEmpty()) {
            throw new EntityNotFoundException("Restaurant", restaurantId.toString());
        }
        return gateway.findByRestaurant(restaurantId, page, size);
    }
}
