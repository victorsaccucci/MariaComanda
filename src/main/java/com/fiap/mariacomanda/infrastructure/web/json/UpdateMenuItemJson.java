package com.fiap.mariacomanda.infrastructure.web.json;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMenuItemJson {

    private String name;

    private String description;

    private BigDecimal price;

    private UUID restaurantId;

    private Boolean dineInOnly;

    private String photoPath;

}
