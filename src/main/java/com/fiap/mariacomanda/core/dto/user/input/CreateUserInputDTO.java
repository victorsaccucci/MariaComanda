package com.fiap.mariacomanda.core.dto.user.input;

import java.util.UUID;

public record CreateUserInputDTO(
    String name,
    String email,
    String password,
    UUID userTypeId
) {}
