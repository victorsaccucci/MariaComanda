package com.fiap.mariacomanda.core.domain.entity;

import lombok.*;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.UUID;

@Getter
@NoArgsConstructor
@ToString(exclude = "passwordHash") // Excluir senha do toString por segurança
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String email;
    private String passwordHash;
    private UserType userType;

    public User(UUID id, String name, String email, String passwordHash, UserType userType) {
        this.id = id;
        this.name = validateName(name);
        this.email = validateEmail(email);
        this.passwordHash = validatePasswordHash(passwordHash);
        this.userType = validateUserType(userType);
    }

    // Getter restrito para passwordHash
    public String getPasswordHash() {
        return passwordHash;
    }

    // Metodo específico para alteração de senha
    public void changePassword(String newPasswordHash) {
        this.passwordHash = validatePasswordHash(newPasswordHash);
    }

    public UUID getUserTypeId() {
        return userType != null ? userType.getId() : null;
    }

    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("User name cannot exceed 100 characters");
        }
        return name.trim();
    }

    private String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("User email cannot be null or empty");
        }

        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (email.length() > 255) {
            throw new IllegalArgumentException("Email cannot exceed 255 characters");
        }
        return email.toLowerCase().trim();
    }

    private String validatePasswordHash(String passwordHash) {
        if (passwordHash != null && passwordHash.trim().isEmpty()) {
            throw new IllegalArgumentException("Password hash cannot be empty");
        }
        return passwordHash;
    }

    private UserType validateUserType(UserType userType) {
        if (userType == null) {
            throw new IllegalArgumentException("UserType cannot be null");
        }
        if (userType.getId() == null) {
            throw new IllegalArgumentException("UserType ID cannot be null");
        }
        return userType;
    }

    // Setters apenas para campos permitidos
    public void setName(String name) {
        this.name = validateName(name);
    }

    public void setEmail(String email) {
        this.email = validateEmail(email);
    }

    public void setUserType(UserType userType) {
        this.userType = validateUserType(userType);
    }
}