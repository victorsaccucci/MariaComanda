package com.fiap.mariacomanda.infrastructure.database.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "user_type")
@Getter
@Setter
public class UserTypeEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(name = "sub_type", nullable = false, length = 30)
    private String subType; // CUSTOMER | OWNER
}