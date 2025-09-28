package com.fiap.mariacomanda.infrastructure.database.mapper.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.RestaurantEntity;
import org.springframework.stereotype.Component;

@Component("restaurantEntityMapper")
public class RestaurantEntityMapper {
    public RestaurantEntity toEntity(Restaurant d) {
        RestaurantEntity e = new RestaurantEntity();
        e.setId(d.getId());
        e.setName(d.getName());
        e.setAddress(d.getAddress());
        e.setCuisineType(d.getCuisineType());
        e.setOpeningHours(d.getOpeningHours());
        e.setOwnerUserId(d.getOwnerUserId());
        return e;
    }

    public Restaurant toDomain(RestaurantEntity e) {
        return new Restaurant(e.getId(), e.getName(), e.getAddress(),
                e.getCuisineType(), e.getOpeningHours(), e.getOwnerUserId());
    }
}