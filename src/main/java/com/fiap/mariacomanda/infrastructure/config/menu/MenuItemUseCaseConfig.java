package com.fiap.mariacomanda.infrastructure.config.menu;

import com.fiap.mariacomanda.core.domain.usecases.menuItem.*;
import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.usecases.common.AuthorizationValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.NullObjectValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.RestaurantValidator;
import com.fiap.mariacomanda.core.domain.usecases.menuItem.impl.*;
import com.fiap.mariacomanda.core.mapper.MenuItemMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuItemUseCaseConfig {

    @Bean
    public RestaurantValidator restaurantValidator(RestaurantGateway restaurantGateway) {
        return new RestaurantValidator(restaurantGateway);
    }

    @Bean
    public CreateMenuItemUseCase createMenuItemUseCase(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway,
                                                    UserGateway userGateway, MenuItemMapper menuItemMapper,
                                                    NullObjectValidator nullObjectValidator, AuthorizationValidator authorizationValidator,
                                                    RestaurantValidator restaurantValidator) {
        return new CreateMenuItemUseCaseImpl(menuItemGateway, restaurantGateway,
                                            userGateway, menuItemMapper, nullObjectValidator,
                                            authorizationValidator, restaurantValidator);
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
    public UpdateMenuItemUseCase updateMenuItemUseCase(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway) {
        return new UpdateMenuItemUseCaseImpl(menuItemGateway, restaurantGateway);
    }
}
