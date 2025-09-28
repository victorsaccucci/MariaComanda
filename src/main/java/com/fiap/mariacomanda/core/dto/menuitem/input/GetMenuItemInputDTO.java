package com.fiap.mariacomanda.core.dto.menuitem.input;

import java.math.BigDecimal;
import java.util.UUID;

public record GetMenuItemInputDTO(
    UUID id,
    UUID restaurantId,
    String name,
    String description,
    BigDecimal price,
    boolean dineInOnly,
    String photoPath
) {}
