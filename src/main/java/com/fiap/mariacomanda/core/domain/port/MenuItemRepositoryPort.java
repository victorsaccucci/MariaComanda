package com.fiap.mariacomanda.core.domain.port;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import java.util.*;

public interface MenuItemRepositoryPort {
    MenuItem save(MenuItem m);
    Optional<MenuItem> findById(UUID id);
    List<MenuItem> findByRestaurant(UUID restaurantId, int page, int size);
    void deleteById(UUID id);
}