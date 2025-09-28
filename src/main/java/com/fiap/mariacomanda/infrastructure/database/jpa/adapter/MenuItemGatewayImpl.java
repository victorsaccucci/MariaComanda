package com.fiap.mariacomanda.infrastructure.database.jpa.adapter;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.MenuItemEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.RestaurantEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.repository.MenuItemJpaRepository;
import com.fiap.mariacomanda.infrastructure.database.jpa.repository.RestaurantJpaRepository;

import com.fiap.mariacomanda.infrastructure.database.mapper.menuitem.MenuItemEntityMapperImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class MenuItemGatewayImpl implements MenuItemGateway {

    private final MenuItemJpaRepository repository;
    private final RestaurantJpaRepository restaurantRepository;
    private final MenuItemEntityMapperImpl mapper;

    public MenuItemGatewayImpl(MenuItemJpaRepository repository, RestaurantJpaRepository restaurantRepository,
                               MenuItemEntityMapperImpl mapper) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.mapper = mapper;
    }

    @Override
    public MenuItem save(MenuItem m) {
        MenuItemEntity entity = mapper.toEntity(m);
        RestaurantEntity restaurant = restaurantRepository.findById(m.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found for id: " + m.getRestaurantId()));
        entity.setRestaurant(restaurant);
        MenuItemEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<MenuItem> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<MenuItem> findByRestaurant(UUID restaurantId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByRestaurant_Id(restaurantId, pageable)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
