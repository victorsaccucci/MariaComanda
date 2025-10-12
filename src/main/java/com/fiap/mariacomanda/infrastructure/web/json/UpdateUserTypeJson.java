package com.fiap.mariacomanda.infrastructure.web.json;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserTypeJson {

    private String name;

    private String subType;
}
