package com.fiap.mariacomanda.application.usecases.restaurant;

import com.fiap.mariacomanda.domain.entity.Restaurant;
import com.fiap.mariacomanda.domain.port.RestaurantRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GetRestaurantUseCase {
    private final RestaurantRepositoryPort repo;
    public GetRestaurantUseCase(RestaurantRepositoryPort repo){ this.repo = repo; }
    public Optional<Restaurant> execute(UUID id){ return repo.findById(id); }
}