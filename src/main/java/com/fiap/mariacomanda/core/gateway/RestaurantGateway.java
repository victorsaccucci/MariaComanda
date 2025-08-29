package com.fiap.mariacomanda.core.gateway;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;

public interface RestaurantGateway {
    Restaurant create(Restaurant restaurant);
}
