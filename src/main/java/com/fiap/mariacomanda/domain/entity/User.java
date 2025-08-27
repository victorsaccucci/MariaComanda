package com.fiap.mariacomanda.domain.entity;

import java.util.UUID;

public record User(UUID id, String name, String email, String passwordHash, UUID userTypeId) {
}