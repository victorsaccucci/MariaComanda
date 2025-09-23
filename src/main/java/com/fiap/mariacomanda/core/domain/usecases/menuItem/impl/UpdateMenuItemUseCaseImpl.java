package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.domain.usecases.menuItem.UpdateMenuItemUseCase;

public class UpdateMenuItemUseCaseImpl implements UpdateMenuItemUseCase {

    private final MenuItemGateway repository;

    public UpdateMenuItemUseCaseImpl(MenuItemGateway repository) {
        this.repository = repository;
    }

    @Override
    public MenuItem execute(MenuItem menuItem) {
        if (menuItem.id() == null) throw new IllegalArgumentException("id is required");
        return repository.save(menuItem);
    }

}
