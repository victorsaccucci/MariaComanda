package com.fiap.mariacomanda.infrastructure.web.json;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateUserJson {

    private String name;

    private String email;

    private String password;

    private UUID userTypeId;
}
