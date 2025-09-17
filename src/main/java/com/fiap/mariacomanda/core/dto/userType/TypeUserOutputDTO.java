package com.fiap.mariacomanda.core.dto.userType;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class TypeUserOutputDTO {
    private UUID id;
    private String name;
}
