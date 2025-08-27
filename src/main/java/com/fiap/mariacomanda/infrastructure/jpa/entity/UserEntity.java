package com.fiap.mariacomanda.infrastructure.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter;
import java.util.UUID;

@Entity @Table(name="app_user")
@Getter @Setter
public class UserEntity {
    @Id @GeneratedValue
    private UUID id;

    @Column(nullable=false, length=120)
    private String name;

    @Column(nullable=false, unique=true, length=120)
    private String email;

    @Column(name="password_hash", nullable=false, length=255)
    private String passwordHash;

    @Column(name="user_type_id", nullable=false)
    private UUID userTypeId;
}