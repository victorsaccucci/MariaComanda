package com.fiap.mariacomanda.infrastructure.config.restaurant;

import com.fiap.mariacomanda.core.domain.usecases.restaurant.*;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.usecases.restaurant.impl.*;
import com.fiap.mariacomanda.core.mapper.RestaurantMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantUseCaseConfig {

    @Bean
    public CreateRestaurantUseCase createRestaurantUsecase(RestaurantGateway restaurantGateway, UserGateway userGateway,
                                                        RestaurantMapper restaurantMapper) {
        return new CreateRestaurantUseCaseImpl(restaurantGateway, userGateway, restaurantMapper);
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
    public UpdateRestaurantUseCase updateRestaurantUsecase(RestaurantGateway restaurantGateway, UserGateway userGateway) {
        return new UpdateRestaurantUseCaseImpl(restaurantGateway, userGateway);
    }
}
