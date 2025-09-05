package com.fiap.mariacomanda.infrastructure.database.jpa.repository;

import com.fiap.mariacomanda.infrastructure.database.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, UUID> {}