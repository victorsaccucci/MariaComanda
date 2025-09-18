package com.fiap.mariacomanda.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fiap.mariacomanda.core.controller.MenuItemController;
import com.fiap.mariacomanda.core.mapper.MenuItemMapper;
import com.fiap.mariacomanda.core.mapper.MenuItemMapperImpl;
import com.fiap.mariacomanda.core.usecases.menuItem.*;

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
