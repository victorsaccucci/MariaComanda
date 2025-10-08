package com.fiap.mariacomanda.core.domain.usecases.common;

import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.UserType;

import java.util.UUID;

/**
 * Validador para regras específicas de tipo de usuário.
 * Responsável por validar existência e regras de negócio relacionadas a tipos de usuário.
 * Segue o princípio da responsabilidade única.
 */
public class UserTypeValidator {

    private final UserTypeGateway userTypeGateway;

    public UserTypeValidator(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    /**
     * Busca e valida se o tipo de usuário existe.
     *
     * @param userTypeId ID do tipo de usuário
     * @return Tipo de usuário encontrado
     * @throws IllegalArgumentException se o ID for null ou tipo não encontrado
     */
    public UserType validateAndFindUserType(UUID userTypeId) {
        if (userTypeId == null) {
            throw new IllegalArgumentException("UserType ID cannot be null");
        }

        return userTypeGateway.findById(userTypeId)
                .orElseThrow(() -> new IllegalArgumentException("UserType not found for id: " + userTypeId));
    }

    /**
     * Valida se o tipo de usuário é válido para criação de usuários.
     * Pode ser estendido para incluir regras específicas de negócio.
     *
     * @param userType Tipo de usuário a ser validado
     * @throws IllegalArgumentException se o tipo for inválido
     */
    public void validateUserTypeForUserCreation(UserType userType) {
        if (userType == null) {
            throw new IllegalArgumentException("UserType cannot be null");
        }

        // Regras específicas podem ser adicionadas aqui
        // Por exemplo: validar se o tipo está ativo, se permite criação, etc.
    }
}