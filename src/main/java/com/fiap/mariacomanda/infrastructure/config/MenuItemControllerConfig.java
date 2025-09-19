package com.fiap.mariacomanda.infrastructure.config;

import org.springframework.context.annotation.Configuration;

import com.fiap.mariacomanda.core.controller.MenuItemController;
import com.fiap.mariacomanda.core.mapper.MenuItemMapper;
import com.fiap.mariacomanda.core.usecases.menuItem.CreateMenuItemUseCase;
import com.fiap.mariacomanda.core.usecases.menuItem.DeleteMenuItemUseCase;
import com.fiap.mariacomanda.core.usecases.menuItem.GetMenuItemUseCase;
import com.fiap.mariacomanda.core.usecases.menuItem.ListMenuItemUseCase;
import com.fiap.mariacomanda.core.usecases.menuItem.UpdateMenuItemUseCase;

@Configuration
public class MenuItemControllerConfig {

    public MenuItemController menuItemController(
            CreateMenuItemUseCase create,
            DeleteMenuItemUseCase delete,
            GetMenuItemUseCase get,
            ListMenuItemUseCase list,
            UpdateMenuItemUseCase update,
            MenuItemMapper menuItemMapper) {
        return new MenuItemController(create, delete, get, list, update, menuItemMapper);
    }
}
