package com.fiap.mariacomanda.core.dto.menuitem.input;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateMenuItemInputDTO(
    String name,
    String description,
    BigDecimal price,
    UUID restaurantId,
    boolean dineInOnly,
    String photoPath
) {}
