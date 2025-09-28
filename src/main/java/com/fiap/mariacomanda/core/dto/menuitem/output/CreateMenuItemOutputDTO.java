package com.fiap.mariacomanda.core.dto.menuitem.output;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateMenuItemOutputDTO(
    UUID id,
    String name,
    String description,
    BigDecimal price,
    UUID restaurantId,
    boolean dineInOnly,
    String photoPath
) {}
