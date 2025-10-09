package com.fiap.mariacomanda.infrastructure.web.json;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateRestaurantJson {

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String address;

    @Size(max = 120)
    private String cuisineType;

    @Size(max = 120)
    private String openingHours;

    private UUID ownerUserId;
}
