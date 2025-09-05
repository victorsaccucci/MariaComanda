package com.fiap.mariacomanda.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fiap.mariacomanda.core.usecases.restaurant.CreateRestaurantUseCase;
import com.fiap.mariacomanda.core.usecases.restaurant.CreateRestaurantUseCaseImpl;
import com.fiap.mariacomanda.core.usecases.restaurant.DeleteRestaurantUseCase;
import com.fiap.mariacomanda.core.usecases.restaurant.DeleteRestaurantUseCaseImpl;
import com.fiap.mariacomanda.core.usecases.restaurant.GetRestaurantUseCase;
import com.fiap.mariacomanda.core.usecases.restaurant.GetRestaurantUseCaseImpl;
import com.fiap.mariacomanda.core.usecases.restaurant.ListRestaurantsUseCase;
import com.fiap.mariacomanda.core.usecases.restaurant.ListRestaurantsUseCaseImpl;
import com.fiap.mariacomanda.core.usecases.restaurant.UpdateRestaurantUseCase;
import com.fiap.mariacomanda.core.usecases.restaurant.UpdateRestaurantUseCaseImpl;

@Configuration
public class RestaurantUseCaseConfig {

    @Bean
    public  CreateRestaurantUseCase createRestaurantUsecase(RestaurantRepositoryPort repositoryPort) {
        return new CreateRestaurantUseCaseImpl(repositoryPort);
   }

   @Bean
    public  DeleteRestaurantUseCase deleteRestaurantUsecase(RestaurantRepositoryPort repositoryPort) {
        return new DeleteRestaurantUseCaseImpl(repositoryPort);
   }

   @Bean
    public  GetRestaurantUseCase getRestaurantUsecase(RestaurantRepositoryPort repositoryPort) {
        return new GetRestaurantUseCaseImpl(repositoryPort);
   }

   @Bean
    public  ListRestaurantsUseCase listRestaurantUsecase(RestaurantRepositoryPort repositoryPort) {
        return new ListRestaurantsUseCaseImpl(repositoryPort);
   }

   @Bean
    public  UpdateRestaurantUseCase updateRestaurantUsecase(RestaurantRepositoryPort repositoryPort) {
        return new UpdateRestaurantUseCaseImpl(repositoryPort);
   }
}
