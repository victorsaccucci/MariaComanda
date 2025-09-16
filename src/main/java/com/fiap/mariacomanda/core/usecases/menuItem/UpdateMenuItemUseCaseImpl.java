package com.fiap.mariacomanda.core.usecases.menuItem;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.gateway.MenuItemGateway;

public class UpdateMenuItemUseCaseImpl implements UpdateMenuItemUseCase {

    private final MenuItemGateway gateway;

    public UpdateMenuItemUseCaseImpl(MenuItemGateway gateway){
        this.gateway = gateway;
    }

    @Override
    public MenuItem execute(MenuItem menuItem) {
        if (menuItem.id() == null) throw new IllegalArgumentException("id is required"); 
        return gateway.save(menuItem);
    }

}
