package com.fiap.mariacomanda.core.adapters.gateway;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import java.util.*;

public interface MenuItemGateway {
    MenuItem save(MenuItem m);
    Optional<MenuItem> findById(UUID id);
    List<MenuItem> findByRestaurant(UUID restaurantId, int page, int size);
    void deleteById(UUID id);
}