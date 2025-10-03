package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.domain.usecases.menuItem.UpdateMenuItemUseCase;

public class UpdateMenuItemUseCaseImpl implements UpdateMenuItemUseCase {

    private final MenuItemGateway gateway;

    public UpdateMenuItemUseCaseImpl(MenuItemGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public MenuItem execute(MenuItem menuItem) {
        if (menuItem.getId() == null) throw new IllegalArgumentException("id is required");
        return gateway.save(menuItem);
    }

}
