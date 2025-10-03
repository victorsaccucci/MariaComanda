package com.fiap.mariacomanda.core.dto.usertype.input;

import java.util.UUID;

public record CreateUserTypeInputDTO(
    UUID id,
    String name,
    String subType
) {}
