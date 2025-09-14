package com.fiap.mariacomanda.infrastructure.database.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
public class RestaurantEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, columnDefinition = "text")
    private String address;

    @Column(name = "cuisine_type", nullable = false, length = 80)
    private String cuisineType;

    @Column(name = "opening_hours", length = 120)
    private String openingHours;

    @Column(name = "owner_user_id", nullable = false)
    private UUID ownerUserId;
}