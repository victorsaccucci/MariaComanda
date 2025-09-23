package com.fiap.mariacomanda.infrastructure.web.json;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class RestaurantJson {
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private String cuisineType;

    @NotNull
    private String openingHours;

    @NotNull
    private UUID ownerUserId;

}