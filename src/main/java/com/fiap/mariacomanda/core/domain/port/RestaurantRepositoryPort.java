package com.fiap.mariacomanda.core.domain.port;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import java.util.*;

public interface RestaurantRepositoryPort {
    Restaurant save(Restaurant r);
    Optional<Restaurant> findById(UUID id);
    List<Restaurant> findAll(int page, int size);
    void deleteById(UUID id);
}