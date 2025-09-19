package com.fiap.mariacomanda.infrastructure.database.jpa.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.gateway.MenuItemGateway;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.MenuItemEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.repository.MenuItemJpaRepository;

@Component
public class MenuItemRepositoryAdapter implements MenuItemGateway {

    private final MenuItemJpaRepository repository;
    private final MenuItemEntityMapper mapper;

    public MenuItemRepositoryAdapter(MenuItemJpaRepository repository, MenuItemEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public MenuItem save(MenuItem m) {
        MenuItemEntity saved = repository.save(mapper.toEntity(m));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<MenuItem> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<MenuItem> findByRestaurant(UUID restaurantId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByRestaurantId(restaurantId, pageable)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id){
        repository.deleteById(id);
    }
}
