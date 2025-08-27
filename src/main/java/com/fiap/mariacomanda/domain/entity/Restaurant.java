package com.fiap.mariacomanda.domain.entity;

import java.util.UUID;

public record Restaurant(UUID id, String name, String address, String cuisineType, String openingHours, UUID ownerUserId) {}