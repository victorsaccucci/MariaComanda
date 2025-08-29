package com.fiap.mariacomanda.core.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuItem(UUID id, UUID restaurantId, String name, String description,
                       BigDecimal price, boolean dineInOnly, String photoPath) {
}