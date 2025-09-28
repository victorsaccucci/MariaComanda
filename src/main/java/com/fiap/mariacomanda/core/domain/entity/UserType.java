package com.fiap.mariacomanda.core.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserType {
    @EqualsAndHashCode.Include
    private UUID id;
    private String typeName;

    // Constantes para tipos padrão
    public static final String CLIENT_TYPE = "CLIENT";
    public static final String RESTAURANT_OWNER_TYPE = "RESTAURANT_OWNER";

    public UserType(UUID id, String typeName) {
        this.id = id;
        this.typeName = validateTypeName(typeName);
    }

    // Validações de negócio
    private String validateTypeName(String typeName) {
        if (typeName == null || typeName.trim().isEmpty()) {
            throw new IllegalArgumentException("UserType name cannot be null or empty");
        }
        if (typeName.length() > 50) {
            throw new IllegalArgumentException("UserType name cannot exceed 50 characters");
        }
        return typeName.trim().toUpperCase();
    }

    // Métodos de conveniência para criar tipos padrão
    public static UserType createClientType(UUID id) {
        return new UserType(id, CLIENT_TYPE);
    }

    public static UserType createRestaurantOwnerType(UUID id) {
        return new UserType(id, RESTAURANT_OWNER_TYPE);
    }

    public boolean isClient() {
        return CLIENT_TYPE.equals(this.typeName);
    }

    public boolean isRestaurantOwner() {
        return RESTAURANT_OWNER_TYPE.equals(this.typeName);
    }

    // Setter com validação
    public void setTypeName(String typeName) {
        this.typeName = validateTypeName(typeName);
    }
}
