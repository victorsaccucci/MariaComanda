package com.fiap.mariacomanda.core.dto.user.output;

import java.util.UUID;

public record CreateUserOutputDTO(
    UUID id,
    String name,
    String email,
    UUID userTypeId
) {}
