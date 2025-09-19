package com.fiap.mariacomanda.core.usecases.menuItem;

import java.util.List;
import java.util.UUID;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.gateway.MenuItemGateway;

public class ListMenuItemUseCaseImpl implements ListMenuItemUseCase {

    private final MenuItemGateway repository;

    public ListMenuItemUseCaseImpl(MenuItemGateway repository) {
        this.repository = repository;
    }

    @Override
    public List<MenuItem> execute(UUID restaurantId, int page, int size){
        return repository.findByRestaurant(restaurantId, page, size);
    }
}
