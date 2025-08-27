package com.fiap.mariacomanda.infrastructure.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Entity @Table(name="menu_item")
@Getter @Setter
public class MenuItemEntity {
    @Id @GeneratedValue
    private UUID id;

    @Column(name="restaurant_id", nullable=false)
    private UUID restaurantId;

    @Column(nullable=false, length=120)
    private String name;

    @Column(columnDefinition="text")
    private String description;

    @Column(nullable=false, precision=10, scale=2)
    private BigDecimal price;

    @Column(name="dine_in_only", nullable=false)
    private boolean dineInOnly;

    @Column(name="photo_path")
    private String photoPath;
}