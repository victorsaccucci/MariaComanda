package com.fiap.mariacomanda.core.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserType {
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String subType; // Valor técnico que diferencia CUSTOMER ou OWNER

    // Constantes para subTypes válidos
    public static final String CUSTOMER = UserTypeEnum.CUSTOMER.getSubTypeName();
    public static final String OWNER = UserTypeEnum.OWNER.getSubTypeName();

    public UserType(UUID id, String name, String subType) {
        this.id = validateId(id);
        this.name = validateName(name);
        this.subType = validateSubType(subType);
    }

    public UserType(String name, String subType) {
        this.name = validateName(name);
        this.subType = validateSubType(subType);
    }

    public static UserType createCustomer(UUID id, String name) {
        return new UserType(id, name, CUSTOMER);
    }

    public static UserType createOwner(UUID id, String name) {
        return new UserType(id, name, OWNER);
    }

    private String validateName(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("UserType name cannot be null or empty");
        }
        if (value.length() > 120) {
            throw new IllegalArgumentException("UserType name cannot exceed 120 characters");
        }
        return value.trim();
    }

    private UUID validateId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("UserType ID cannot be null");
        }
        return value;
    }

    private String validateSubType(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("UserType subType cannot be null or empty");
        }
        String normalized = value.trim();
        try {
            return UserTypeEnum.fromSubTypeName(normalized).getSubTypeName();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid user subType: " + value + ". Allowed: CUSTOMER, OWNER", ex);
        }
    }

    public boolean isCustomer() {
        return CUSTOMER.equals(this.subType);
    }

    public boolean isOwner() {
        return OWNER.equals(this.subType);
    }

    public void setName(String name) {
        this.name = validateName(name);
    }

    public void setSubType(String subType) {
        this.subType = validateSubType(subType);
    }
}
