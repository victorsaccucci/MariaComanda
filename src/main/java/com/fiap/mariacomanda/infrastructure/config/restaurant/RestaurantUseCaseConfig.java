package com.fiap.mariacomanda.infrastructure.config.restaurant;

import com.fiap.mariacomanda.core.domain.usecases.restaurant.*;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.domain.usecases.restaurant.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantUseCaseConfig {

    @Bean
    public CreateRestaurantUseCase createRestaurantUsecase(RestaurantGateway restaurantGateway) {
        return new CreateRestaurantUseCaseImpl(restaurantGateway);
    }

    @Bean
    public DeleteRestaurantUseCase deleteRestaurantUsecase(RestaurantGateway restaurantGateway) {
        return new DeleteRestaurantUseCaseImpl(restaurantGateway);
    }

    @Bean
    public GetRestaurantUseCase getRestaurantUsecase(RestaurantGateway restaurantGateway) {
        return new GetRestaurantUseCaseImpl(restaurantGateway);
    }

    @Bean
    public ListRestaurantsUseCase listRestaurantUsecase(RestaurantGateway restaurantGateway) {
        return new ListRestaurantsUseCaseImpl(restaurantGateway);
    }

    @Bean
    public UpdateRestaurantUseCase updateRestaurantUsecase(RestaurantGateway restaurantGateway) {
        return new UpdateRestaurantUseCaseImpl(restaurantGateway);
    }
}
