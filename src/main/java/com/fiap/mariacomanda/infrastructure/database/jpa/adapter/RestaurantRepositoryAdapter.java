package com.fiap.mariacomanda.infrastructure.database.jpa.adapter;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.port.RestaurantRepositoryPort;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.RestaurantEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.repository.RestaurantJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RestaurantRepositoryAdapter implements RestaurantRepositoryPort {

    private final RestaurantJpaRepository repo;
    private final RestaurantMapper mapper;

    public RestaurantRepositoryAdapter(RestaurantJpaRepository repo, RestaurantMapper mapper) {
        this.repo = repo; this.mapper = mapper;
    }

    @Override
    public Restaurant save(Restaurant r) {
        RestaurantEntity saved = repo.save(mapper.toEntity(r));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Restaurant> findById(UUID id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Restaurant> findAll(int page, int size) {
        return repo.findAll(PageRequest.of(page, size))
                .map(mapper::toDomain)
                .getContent();
    }

    @Override
    public void deleteById(UUID id) { repo.deleteById(id); }
}