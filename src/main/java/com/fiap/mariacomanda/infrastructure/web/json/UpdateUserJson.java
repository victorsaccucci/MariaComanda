package com.fiap.mariacomanda.infrastructure.web.json;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateUserJson {

    @Size(max = 100)
    private String name;

    @Email
    @Size(max = 255)
    private String email;

    @Size(min = 6, max = 255)
    private String password;

    private UUID userTypeId;
}
