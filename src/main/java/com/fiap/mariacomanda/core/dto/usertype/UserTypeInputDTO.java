package com.fiap.mariacomanda.core.dto.usertype;

import java.util.UUID;

public record UserTypeInputDTO(
    UUID id,
    String typeName
) {}
