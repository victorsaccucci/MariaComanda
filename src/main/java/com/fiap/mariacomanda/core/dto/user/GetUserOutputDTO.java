package com.fiap.mariacomanda.core.dto.user;

import com.fiap.mariacomanda.core.domain.entity.UserType;

import java.util.UUID;

public class GetUserOutputDTO {
    private UUID id;
    private String name;
    private String email;
    private String passwordHash;
    private UserType userType;

    public GetUserOutputDTO(UUID id, String name, String email, String passwordHash, UserType userType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.userType = userType;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserType getUserType() {
        return userType;
    }
}
