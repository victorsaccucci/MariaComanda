package com.fiap.mariacomanda.infrastructure.web.dto;

import java.util.UUID;

public record RestaurantDTO(UUID id, String name, String address, String cuisineType,
                            String openingHours, UUID ownerUserId) {}