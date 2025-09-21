package com.fiap.mariacomanda.infrastructure.config;

import com.fiap.mariacomanda.core.domain.usecases.menuItem.*;
import com.fiap.mariacomanda.core.gateway.MenuItemGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuItemUseCaseConfig {

    @Bean
    public CreateMenuItemUseCase createMenuItemUseCase(MenuItemGateway menuItemGateway) {
        return new CreateMenuItemUseCaseImpl(menuItemGateway);
    }

    @Bean
    public DeleteMenuItemUseCase deleteMenuItemUseCase(MenuItemGateway menuItemGateway) {
        return new DeleteMenuItemUseCaseImpl(menuItemGateway);
    }

    @Bean
    public GetMenuItemUseCase getMenuItemUseCase(MenuItemGateway menuItemGateway) {
        return new GetMenuItemUseCaseImpl(menuItemGateway);
    }

    @Bean
    public ListMenuItemUseCase listMenuItemUseCase(MenuItemGateway menuItemGateway) {
        return new ListMenuItemUseCaseImpl(menuItemGateway);
    }

    @Bean
    public UpdateMenuItemUseCase updateMenuItemUseCase(MenuItemGateway menuItemGateway) {
        return new UpdateMenuItemUseCaseImpl(menuItemGateway);
    }
}
