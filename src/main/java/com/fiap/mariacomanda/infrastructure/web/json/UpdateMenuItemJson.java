package com.fiap.mariacomanda.infrastructure.web.json;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMenuItemJson {

    @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String description;

    private BigDecimal price;

    private UUID restaurantId;

    private boolean dineInOnly;

    @Size(max = 255)
    private String photoPath;

}
