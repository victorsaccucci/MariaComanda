package com.fiap.mariacomanda.infrastructure.database.jpa.repository;

import com.fiap.mariacomanda.infrastructure.database.jpa.entity.MenuItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MenuItemJpaRepository extends JpaRepository<MenuItemEntity, UUID> {
    Page<MenuItemEntity> findByRestaurantId(UUID restaurantId, Pageable pageable);
}
