package com.fiap.mariacomanda.infrastructure.config;

import com.fiap.mariacomanda.core.adapters.controller.MenuItemController;
import com.fiap.mariacomanda.core.domain.usecases.menuItem.*;
import com.fiap.mariacomanda.core.mapper.MenuItemMapper;
import com.fiap.mariacomanda.core.mapper.MenuItemMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuItemControllerConfig {

    @Bean
    public MenuItemMapper menuItemMapper() {
        return new MenuItemMapperImpl();
    }

    @Bean
    public MenuItemController menuItemController(
            CreateMenuItemUseCase create,
            DeleteMenuItemUseCase delete,
            GetMenuItemUseCase get,
            ListMenuItemUseCase list,
            UpdateMenuItemUseCase update,
            MenuItemMapper menuItemMapper

    ) {
        return new MenuItemController(create, delete, get, list, update, menuItemMapper);
    }
}
