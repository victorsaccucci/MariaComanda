package com.fiap.mariacomanda.core.dto.restaurant.output;

import java.util.UUID;

public record GetRestaurantOutputDTO(
    UUID id,
    String name,
    String address,
    String cuisineType,
    String openingHours,
    UUID ownerUserId
) {}
