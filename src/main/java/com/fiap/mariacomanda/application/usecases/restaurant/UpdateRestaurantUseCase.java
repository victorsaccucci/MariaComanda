package com.fiap.mariacomanda.application.usecases.restaurant;

import com.fiap.mariacomanda.domain.entity.Restaurant;
import com.fiap.mariacomanda.domain.port.RestaurantRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateRestaurantUseCase {
    private final RestaurantRepositoryPort repo;
    public UpdateRestaurantUseCase(RestaurantRepositoryPort repo){ this.repo = repo; }

    @Transactional
    public Restaurant execute(Restaurant r){
        if (r.id() == null) throw new IllegalArgumentException("id is required");
        return repo.save(r);
    }
}