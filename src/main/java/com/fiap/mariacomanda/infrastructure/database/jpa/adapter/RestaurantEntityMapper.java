package com.fiap.mariacomanda.infrastructure.database.jpa.adapter;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.RestaurantEntity;
import org.springframework.stereotype.Component;

@Component("restaurantEntityMapper")
public class RestaurantEntityMapper {
    public RestaurantEntity toEntity(Restaurant d) {
        var e = new RestaurantEntity();
        e.setId(d.id());
        e.setName(d.name());
        e.setAddress(d.address());
        e.setCuisineType(d.cuisineType());
        e.setOpeningHours(d.openingHours());
        e.setOwnerUserId(d.ownerUserId());
        return e;
    }
    public Restaurant toDomain(RestaurantEntity e) {
        return new Restaurant(e.getId(), e.getName(), e.getAddress(),
                e.getCuisineType(), e.getOpeningHours(), e.getOwnerUserId());
    }
}