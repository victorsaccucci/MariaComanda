package com.fiap.mariacomanda.infrastructure.database.jpa.adapter;

import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.MenuItemEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.RestaurantEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.repository.MenuItemJpaRepository;
import com.fiap.mariacomanda.infrastructure.database.jpa.repository.RestaurantJpaRepository;
import com.fiap.mariacomanda.infrastructure.database.mapper.menuitem.MenuItemEntityMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class MenuItemGatewayImpl implements MenuItemGateway {

    private final MenuItemJpaRepository menuItemJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final MenuItemEntityMapper menuItemEntityMapper;

    public MenuItemGatewayImpl(MenuItemJpaRepository menuItemJpaRepository, RestaurantJpaRepository restaurantJpaRepository,
                               MenuItemEntityMapper menuItemEntityMapper) {
        this.menuItemJpaRepository = menuItemJpaRepository;
        this.restaurantJpaRepository = restaurantJpaRepository;
        this.menuItemEntityMapper = menuItemEntityMapper;
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        MenuItemEntity entity = menuItemEntityMapper.toEntity(menuItem);
        RestaurantEntity restaurant = restaurantJpaRepository.findById(menuItem.getRestaurantId())
                .orElseThrow(() -> new IllegalStateException("Restaurant not found for id: " + menuItem.getRestaurantId()));
        entity.setRestaurant(restaurant);
        MenuItemEntity saved = menuItemJpaRepository.save(entity);
        return menuItemEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<MenuItem> findById(UUID id) {
        return menuItemJpaRepository.findById(id).map(menuItemEntityMapper::toDomain);
    }

    @Override
    public List<MenuItem> findByRestaurant(UUID restaurantId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return menuItemJpaRepository.findByRestaurant_Id(restaurantId, pageable)
                .stream()
                .map(menuItemEntityMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        menuItemJpaRepository.deleteById(id);
    }
}
