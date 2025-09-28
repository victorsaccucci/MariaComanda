package com.fiap.mariacomanda.infrastructure.database.jpa.repository;

import com.fiap.mariacomanda.infrastructure.database.jpa.entity.RestaurantEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
}