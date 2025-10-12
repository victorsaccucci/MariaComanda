package com.fiap.mariacomanda.core.domain.entity;

import lombok.Getter;

/**
 * Enum para representar os tipos de usuário disponíveis no sistema.
 * Usado em conjunto com a entidade UserType para validações e lógica de negócio.
 */
@Getter
public enum UserTypeEnum {
    CUSTOMER("CUSTOMER", "Standard customer user"),
    OWNER("OWNER", "Restaurant owner with management privileges");

    private final String subTypeName;
    private final String description;

    UserTypeEnum(String subTypeName, String description) {
        this.subTypeName = subTypeName;
        this.description = description;
    }

    public static UserTypeEnum fromSubTypeName(String subTypeName) {
        for (UserTypeEnum type : values()) {
            if (type.subTypeName.equalsIgnoreCase(subTypeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown user subType: " + subTypeName);
    }
}
