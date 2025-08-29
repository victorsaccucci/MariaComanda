package com.fiap.mariacomanda.core.usecases.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.port.RestaurantRepositoryPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListRestaurantsUseCaseImpl implements ListRestaurantsUseCase {
    private final RestaurantRepositoryPort repository;

    public ListRestaurantsUseCaseImpl(RestaurantRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Restaurant> execute(int page, int size) {
        return repository.findAll(page, size);
    }
}