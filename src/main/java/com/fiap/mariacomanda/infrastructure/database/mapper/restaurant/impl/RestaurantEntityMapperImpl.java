package com.fiap.mariacomanda.infrastructure.database.mapper.restaurant.impl;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.RestaurantEntity;
import com.fiap.mariacomanda.infrastructure.database.mapper.restaurant.RestaurantEntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("restaurantEntityMapper")
public class RestaurantEntityMapperImpl implements RestaurantEntityMapper {

    @Override
    public RestaurantEntity toEntity(Restaurant domain) {
        RestaurantEntity entity = new RestaurantEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setAddress(domain.getAddress());
        entity.setCuisineType(domain.getCuisineType());
        entity.setOpeningHours(domain.getOpeningHours());
        entity.setOwnerUserId(domain.getOwnerUserId());
        return entity;
    }

    @Override
    public Restaurant toDomain(RestaurantEntity entity) {
        return new Restaurant(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getCuisineType(),
                entity.getOpeningHours(),
                entity.getOwnerUserId()
        );
    }

    @Override
    public List<Restaurant> toDomainList(List<RestaurantEntity> entities) {
        return entities.stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<RestaurantEntity> toEntityList(List<Restaurant> domains) {
        return domains.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
