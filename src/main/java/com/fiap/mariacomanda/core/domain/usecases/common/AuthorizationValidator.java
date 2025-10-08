package com.fiap.mariacomanda.core.domain.usecases.common;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;

import java.util.UUID;

/**
 * Validador para operações de autorização.
 * Responsável por validar permissões e autorização de usuários.
 * Segue o princípio da responsabilidade única.
 */
public class AuthorizationValidator {

    private final UserGateway userGateway;

    public AuthorizationValidator(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    /**
     * Valida se o requesterUserId não é null e busca o usuário requester.
     *
     * @param requesterUserId ID do usuário que está fazendo a requisição
     * @return Usuário requester encontrado
     * @throws IllegalArgumentException se o ID for null ou usuário não encontrado
     */
    public User validateAndResolveRequester(UUID requesterUserId) {
        if (requesterUserId == null) {
            throw new IllegalArgumentException("Requester user ID cannot be null");
        }

        return userGateway.findById(requesterUserId)
                .orElseThrow(() -> new IllegalArgumentException("Requester user not found"));
    }

    /**
     * Valida se o usuário requester é do tipo OWNER.
     *
     * @param requester Usuário que está fazendo a requisição
     * @param operation Descrição da operação para mensagem de erro
     * @throws IllegalStateException se o usuário não for OWNER
     */
    public void validateRequesterIsOwner(User requester, String operation) {
        if (requester == null) {
            throw new IllegalArgumentException("Requester user cannot be null");
        }

        UserType requesterType = requester.getUserType();
        if (requesterType == null || !requesterType.isOwner()) {
            throw new IllegalStateException("Only OWNER users can " + operation);
        }
    }

    /**
     * Método conveniente que combina validação de requester e autorização OWNER.
     *
     * @param requesterUserId ID do usuário que está fazendo a requisição
     * @param operation Descrição da operação para mensagem de erro
     * @return Usuário requester validado como OWNER
     * @throws IllegalArgumentException se o ID for null ou usuário não encontrado
     * @throws IllegalStateException se o usuário não for OWNER
     */
    public User validateRequesterAndAuthorizeOwner(UUID requesterUserId, String operation) {
        User requester = validateAndResolveRequester(requesterUserId);
        validateRequesterIsOwner(requester, operation);
        return requester;
    }
}
