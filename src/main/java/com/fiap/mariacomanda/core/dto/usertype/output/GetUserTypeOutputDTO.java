package com.fiap.mariacomanda.core.dto.usertype.output;

import java.util.UUID;

public record GetUserTypeOutputDTO(
    UUID id,
    String name,
    String subType
) {}
