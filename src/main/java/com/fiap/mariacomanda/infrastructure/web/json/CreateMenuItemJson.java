package com.fiap.mariacomanda.infrastructure.web.json;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class CreateMenuItemJson {

    private UUID restaurantId;

    private String name;

    private String description;

    private BigDecimal price;

    private Boolean dineInOnly;

    private String photoPath;

}
