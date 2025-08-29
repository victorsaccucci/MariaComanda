package com.fiap.mariacomanda.core.usecases.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.port.RestaurantRepositoryPort;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GetRestaurantUseCaseImpl implements GetRestaurantUseCase {
    private final RestaurantRepositoryPort repository;

    public GetRestaurantUseCaseImpl(RestaurantRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Restaurant> execute(UUID id) {
        return repository.findById(id);
    }
}
