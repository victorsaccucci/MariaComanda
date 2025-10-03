package com.fiap.mariacomanda.core.dto.user.output;

import com.fiap.mariacomanda.core.dto.usertype.output.GetUserTypeOutputDTO;
import java.util.UUID;

public record CreateUserOutputDTO(
    UUID id,
    String name,
    String email,
    GetUserTypeOutputDTO userType
) {}
