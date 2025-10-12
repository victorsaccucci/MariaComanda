package com.fiap.mariacomanda.infrastructure.database.mapper.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.RestaurantEntity;

import java.util.List;

public interface RestaurantEntityMapper {

    RestaurantEntity toEntity(Restaurant domain);

    Restaurant toDomain(RestaurantEntity entity);

    List<Restaurant> toDomainList(List<RestaurantEntity> entities);

    List<RestaurantEntity> toEntityList(List<Restaurant> domains);
}
