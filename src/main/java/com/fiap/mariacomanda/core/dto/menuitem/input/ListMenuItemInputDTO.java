package com.fiap.mariacomanda.core.dto.menuitem.input;

import java.util.UUID;

public record ListMenuItemInputDTO(UUID restaurantId, int page, int size) {}
