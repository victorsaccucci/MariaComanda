package com.fiap.mariacomanda.application.usecases.restaurant;

import com.fiap.mariacomanda.domain.entity.Restaurant;
import com.fiap.mariacomanda.domain.port.RestaurantRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateRestaurantUseCase {
    private final RestaurantRepositoryPort repo;
    public CreateRestaurantUseCase(RestaurantRepositoryPort repo){ this.repo = repo; }

    @Transactional
    public Restaurant execute(Restaurant r){
        if (r.name() == null || r.name().isBlank()) throw new IllegalArgumentException("name is required");
        if (r.address() == null || r.address().isBlank()) throw new IllegalArgumentException("address is required");
        if (r.cuisineType() == null || r.cuisineType().isBlank()) throw new IllegalArgumentException("cuisineType is required");
        if (r.ownerUserId() == null) throw new IllegalArgumentException("ownerUserId is required");
        return repo.save(r);
    }
}