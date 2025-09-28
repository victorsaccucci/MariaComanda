package com.fiap.mariacomanda.core.domain.entity;

import lombok.Getter;

/**
 * Enum para representar os tipos de usuário disponíveis no sistema.
 * Usado em conjunto com a entidade UserType para validações e lógica de negócio.
 */
@Getter
public enum UserTypeEnum {
    CLIENT("CLIENT", "Standard client user"),
    RESTAURANT_OWNER("RESTAURANT_OWNER", "Restaurant owner with management privileges");

    private final String typeName;
    private final String description;

    UserTypeEnum(String typeName, String description) {
        this.typeName = typeName;
        this.description = description;
    }

    public static UserTypeEnum fromTypeName(String typeName) {
        for (UserTypeEnum type : values()) {
            if (type.typeName.equals(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown user type: " + typeName);
    }
}
