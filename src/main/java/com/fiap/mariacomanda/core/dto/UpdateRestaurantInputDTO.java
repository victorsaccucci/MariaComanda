package com.fiap.mariacomanda.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateRestaurantInputDTO {
    private UUID id;
    private String name;
    private String address;
    private String cuisineType;
    private String openingHours;
    private UUID ownerUserId;
}