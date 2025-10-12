package com.fiap.mariacomanda.core.dto.usertype.input;

import java.util.UUID;

public record UpdateUserTypeInputDTO(
    UUID id,
    String name,
    String subType
) {}
