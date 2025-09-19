package com.fiap.mariacomanda.infrastructure.database.jpa.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.MenuItemEntity;

public interface MenuItemJpaRepository extends JpaRepository<MenuItemEntity, UUID> {
    Page<MenuItemEntity> findByRestaurantId(UUID restaurantId, Pageable pageable);
}
