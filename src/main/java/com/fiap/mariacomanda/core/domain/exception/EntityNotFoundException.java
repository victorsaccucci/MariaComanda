package com.fiap.mariacomanda.core.domain.exception;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(String entityName, String identifier) {
        super(String.format("%s not found with identifier: %s", entityName, identifier));
    }
}