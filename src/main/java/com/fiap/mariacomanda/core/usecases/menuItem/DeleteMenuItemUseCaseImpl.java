package com.fiap.mariacomanda.core.usecases.menuItem;

import java.util.UUID;
import com.fiap.mariacomanda.core.gateway.MenuItemGateway;

public class DeleteMenuItemUseCaseImpl implements DeleteMenuItemUseCase {

    private final MenuItemGateway gateway;

    public DeleteMenuItemUseCaseImpl(MenuItemGateway gateway){
        this.gateway = gateway;
    }

    @Override
    public void execute(UUID id) {
        gateway.deleteById(id);
    }
}
