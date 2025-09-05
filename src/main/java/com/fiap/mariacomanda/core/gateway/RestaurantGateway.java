package com.fiap.mariacomanda.core.gateway;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantGateway {
    Restaurant save(Restaurant r);
    Optional<Restaurant> findById(UUID id);
    List<Restaurant> findAll(int page, int size);
    void deleteById(UUID id);
}
