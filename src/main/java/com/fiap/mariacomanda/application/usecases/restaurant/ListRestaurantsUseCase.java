package com.fiap.mariacomanda.application.usecases.restaurant;

import com.fiap.mariacomanda.domain.entity.Restaurant;
import com.fiap.mariacomanda.domain.port.RestaurantRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ListRestaurantsUseCase {
    private final RestaurantRepositoryPort repo;
    public ListRestaurantsUseCase(RestaurantRepositoryPort repo){ this.repo = repo; }
    public List<Restaurant> execute(int page, int size){ return repo.findAll(page, size); }
}