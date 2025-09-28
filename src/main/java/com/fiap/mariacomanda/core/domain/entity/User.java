package com.fiap.mariacomanda.core.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.UUID;

@Getter
@Setter
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
        this.userType = userType;
    }

    // Validações de negócio
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

    // Sobrescrevendo setters do Lombok apenas para campos que precisam de validação
    public void setName(String name) {
        this.name = validateName(name);
    }

    public void setEmail(String email) {
        this.email = validateEmail(email);
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = validatePasswordHash(passwordHash);
    }
}