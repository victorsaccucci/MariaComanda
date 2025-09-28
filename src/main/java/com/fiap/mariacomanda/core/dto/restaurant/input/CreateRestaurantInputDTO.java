package com.fiap.mariacomanda.core.dto.restaurant.input;

import java.util.UUID;

public record CreateRestaurantInputDTO(
    UUID id,
    String name,
    String address,
    String cuisineType,
    String openingHours,
    UUID ownerUserId
) {}
