package com.fiap.mariacomanda.core.domain.entity;

import java.util.UUID;

public record User(UUID id, String name, String email, String passwordHash, UserType userType) {
}