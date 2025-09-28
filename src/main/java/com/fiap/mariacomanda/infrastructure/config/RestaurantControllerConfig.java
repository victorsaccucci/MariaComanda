package com.fiap.mariacomanda.infrastructure.config;

import com.fiap.mariacomanda.core.adapters.controller.RestaurantController;
import com.fiap.mariacomanda.core.domain.usecases.restaurant.*;
import com.fiap.mariacomanda.core.mapper.RestaurantMapper;
import com.fiap.mariacomanda.core.mapper.impl.RestaurantMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantControllerConfig {

    @Bean
    public RestaurantMapper restaurantMapper() {
        return new RestaurantMapperImpl();
    }

    @Bean
    public RestaurantController restaurantController(
            CreateRestaurantUseCase createUseCase,
            GetRestaurantUseCase getUseCase,
            ListRestaurantsUseCase listUseCase,
            UpdateRestaurantUseCase updateUseCase,
            DeleteRestaurantUseCase deleteUseCase,
            RestaurantMapper restaurantMapper
    ) {
        return new RestaurantController(createUseCase, getUseCase, listUseCase, updateUseCase, deleteUseCase, restaurantMapper);
    }
}
