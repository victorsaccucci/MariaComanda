package com.fiap.mariacomanda.infrastructure.config;

import com.fiap.mariacomanda.infrastructure.config.menu.*;
import com.fiap.mariacomanda.infrastructure.config.restaurant.*;
import com.fiap.mariacomanda.infrastructure.config.user.*;
import com.fiap.mariacomanda.infrastructure.config.usertype.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AllConfigTest {

    @Test
    void testMenuConfigs() {
        MenuItemControllerConfig controllerConfig = new MenuItemControllerConfig();
        MenuItemUseCaseConfig useCaseConfig = new MenuItemUseCaseConfig();
        
        assert controllerConfig.menuItemMapper() != null;
        assert controllerConfig.menuItemController(null, null, null, null, null, null) != null;
        
        assert useCaseConfig.createMenuItemUseCase(null, null, null, null) != null;
        assert useCaseConfig.deleteMenuItemUseCase(null, null, null) != null;
        assert useCaseConfig.getMenuItemUseCase(null) != null;
        assert useCaseConfig.listMenuItemUseCase(null, null) != null;
        assert useCaseConfig.updateMenuItemUseCase(null, null, null) != null;
    }

    @Test
    void testRestaurantConfigs() {
        RestaurantControllerConfig controllerConfig = new RestaurantControllerConfig();
        RestaurantUseCaseConfig useCaseConfig = new RestaurantUseCaseConfig();
        
        assert controllerConfig.restaurantMapper() != null;
        assert controllerConfig.restaurantController(null, null, null, null, null, null) != null;
        
        assert useCaseConfig.createRestaurantUsecase(null, null, null) != null;
        assert useCaseConfig.deleteRestaurantUsecase(null, null) != null;
        assert useCaseConfig.getRestaurantUsecase(null) != null;
        assert useCaseConfig.listRestaurantUsecase(null) != null;
        assert useCaseConfig.updateRestaurantUsecase(null, null) != null;
    }

    @Test
    void testUserConfigs() {
        UserControllerConfig controllerConfig = new UserControllerConfig();
        UserUseCaseConfig useCaseConfig = new UserUseCaseConfig();
        
        assert controllerConfig.userMapper() != null;
        assert controllerConfig.userController(null, null, null, null, null, null) != null;
        
        assert useCaseConfig.createUserUsecase(null, null, null) != null;
        assert useCaseConfig.deleteUserUsecase(null) != null;
        assert useCaseConfig.getUserUsecase(null) != null;
        assert useCaseConfig.listUserUsecase(null) != null;
        assert useCaseConfig.updateUserUsecase(null, null) != null;
    }

    @Test
    void testUserTypeConfigs() {
        UserTypeControllerConfig controllerConfig = new UserTypeControllerConfig();
        UserTypeUseCaseConfig useCaseConfig = new UserTypeUseCaseConfig();
        
        assert controllerConfig.userTypeMapper() != null;
        assert controllerConfig.userTypeController(null, null, null, null, null, null) != null;
        
        assert useCaseConfig.createUserTypeUsecase(null, null, null) != null;
        assert useCaseConfig.deleteUserTypeUsecase(null, null) != null;
        assert useCaseConfig.getUserTypeUsecase(null) != null;
        assert useCaseConfig.listUserTypeUsecase(null) != null;
        assert useCaseConfig.updateUserTypeUsecase(null, null) != null;
    }
}