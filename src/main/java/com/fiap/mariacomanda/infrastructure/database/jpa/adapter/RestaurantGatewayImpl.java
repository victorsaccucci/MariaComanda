package com.fiap.mariacomanda.infrastructure.database.jpa.adapter;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.RestaurantEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.repository.RestaurantJpaRepository;
import com.fiap.mariacomanda.infrastructure.database.mapper.restaurant.RestaurantEntityMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RestaurantGatewayImpl implements RestaurantGateway {

    private final RestaurantJpaRepository repo;
    private final RestaurantEntityMapper restaurantEntityMapper;

    public RestaurantGatewayImpl(RestaurantJpaRepository repo, RestaurantEntityMapper restaurantEntityMapper) {
        this.repo = repo;
        this.restaurantEntityMapper = restaurantEntityMapper;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        RestaurantEntity restaurantEntity = restaurantEntityMapper.toEntity(restaurant);
        RestaurantEntity saved = repo.save(restaurantEntity);
        return restaurantEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Restaurant> findById(UUID id) {
        return repo.findById(id).map(restaurantEntityMapper::toDomain);
    }

    @Override
    public List<Restaurant> findAll(int page, int size) {
        return repo.findAll(PageRequest.of(page, size))
                .map(restaurantEntityMapper::toDomain)
                .getContent();
    }

    @Override
    public void deleteById(UUID id) {
        repo.deleteById(id);
    }
}