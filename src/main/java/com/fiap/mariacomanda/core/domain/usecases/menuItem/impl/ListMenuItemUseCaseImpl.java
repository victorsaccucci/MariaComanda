package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.domain.usecases.menuItem.ListMenuItemUseCase;

import java.util.List;
import java.util.UUID;

public class ListMenuItemUseCaseImpl implements ListMenuItemUseCase {

    private final MenuItemGateway gateway;

    public ListMenuItemUseCaseImpl(MenuItemGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<MenuItem> execute(UUID restaurantId, int page, int size) {
        return gateway.findByRestaurant(restaurantId, page, size);
    }
}
