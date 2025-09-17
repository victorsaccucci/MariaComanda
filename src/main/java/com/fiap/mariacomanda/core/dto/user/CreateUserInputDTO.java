package com.fiap.mariacomanda.core.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateUserInputDTO {
    private UUID id;
    private String name;
    private String email;
    private String passwordHash;
    private UUID userTypeId;
}
