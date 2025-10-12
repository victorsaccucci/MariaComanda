package com.fiap.mariacomanda.core.domain.usecases.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;

import java.util.List;

public interface ListRestaurantsUseCase {
    List<Restaurant> execute(int page, int size);
}