package com.fiap.mariacomanda.core.usecases.menuItem;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.gateway.MenuItemGateway;

public class CreateMenuItemUseCaseImpl implements CreateMenuItemUseCase {

    private final MenuItemGateway gateway;

    public CreateMenuItemUseCaseImpl(MenuItemGateway gateway) {
        this.gateway = gateway;
    }

    @Override 
    public MenuItem execute(MenuItem newMenuItem) {
        return gateway.save(newMenuItem);
    }
}
