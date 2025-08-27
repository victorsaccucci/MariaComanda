package com.fiap.mariacomanda.application.usecases.restaurant;

import com.fiap.mariacomanda.domain.port.RestaurantRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class DeleteRestaurantUseCase {
    private final RestaurantRepositoryPort repo;
    public DeleteRestaurantUseCase(RestaurantRepositoryPort repo){ this.repo = repo; }

    @Transactional
    public void execute(UUID id){ repo.deleteById(id); }
}