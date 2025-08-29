package com.fiap.mariacomanda.core.domain.entity;

import java.util.UUID;

public record Restaurant(UUID id, String name, String address, String cuisineType, String openingHours, UUID ownerUserId) {}