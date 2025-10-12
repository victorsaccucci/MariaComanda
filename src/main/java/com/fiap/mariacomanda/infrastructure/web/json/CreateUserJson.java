package com.fiap.mariacomanda.infrastructure.web.json;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class CreateUserJson {

    private String name;

    private String email;

    private String password;

    private UUID userTypeId;
}
