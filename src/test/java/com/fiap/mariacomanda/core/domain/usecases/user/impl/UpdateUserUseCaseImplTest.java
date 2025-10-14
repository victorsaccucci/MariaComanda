package com.fiap.mariacomanda.core.domain.usecases.user.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.exception.ValidationException;
import com.fiap.mariacomanda.core.dto.user.input.UpdateUserInputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UpdateUserUseCaseImplTest {

    private UserGateway userGateway;
    private UserTypeGateway userTypeGateway;
    private UpdateUserUseCaseImpl useCase;

    private UUID requesterId;
    private UUID targetUserId;
    private UUID newUserTypeId;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        userTypeGateway = mock(UserTypeGateway.class);
        useCase = new UpdateUserUseCaseImpl(userGateway, userTypeGateway);

        requesterId = UUID.randomUUID();
        targetUserId = UUID.randomUUID();
        newUserTypeId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve atualizar todos os campos do usuário com sucesso")
    void deveAtualizarTodosCampos() {
        UserType oldType = new UserType(UUID.randomUUID(), "USER", UserType.OWNER);
        UserType newType = new UserType(newUserTypeId, "ADMIN", UserType.OWNER);
        User requester = new User(requesterId, "Owner", "owner@test.com", "senha", new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER));
        User existing = new User(targetUserId, "John", "john@test.com", "senha", oldType);

        UpdateUserInputDTO dto = new UpdateUserInputDTO(targetUserId, "Jane", "jane@test.com", "123456", newUserTypeId);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(userGateway.findById(targetUserId)).thenReturn(Optional.of(existing));
        when(userTypeGateway.findById(newUserTypeId)).thenReturn(Optional.of(newType));
        when(userGateway.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updated = useCase.execute(dto, requesterId);

        assertEquals("Jane", updated.getName());
        assertEquals("jane@test.com", updated.getEmail());
        assertEquals("123456", updated.getPasswordHash());
        assertEquals(newType, updated.getUserType());
    }

    @Test
    @DisplayName("Deve lançar exceção quando inputDTO for nulo")
    void deveLancarExcecaoInputNulo() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> useCase.execute(null, requesterId));
        assertEquals("com.fiap.mariacomanda.core.dto.user.input.UpdateUserInputDTO cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando requester não encontrado")
    void deveLancarExcecaoRequesterNaoEncontrado() {
        UpdateUserInputDTO dto = new UpdateUserInputDTO(targetUserId, "Jane", "jane@test.com", "123456", newUserTypeId);
        when(userGateway.findById(requesterId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(dto, requesterId));
        assertEquals("Requester user not found", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário a ser atualizado não encontrado")
    void deveLancarExcecaoUsuarioNaoEncontrado() {
        UpdateUserInputDTO dto = new UpdateUserInputDTO(targetUserId, "Jane", "jane@test.com", "123456", newUserTypeId);
        User requester = new User(requesterId, "Owner", "owner@test.com", "senha", new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER));

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(userGateway.findById(targetUserId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(dto, requesterId));
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando novo UserType não for encontrado")
    void deveLancarExcecaoUserTypeNaoEncontrado() {
        UpdateUserInputDTO dto = new UpdateUserInputDTO(targetUserId, "Jane", "jane@test.com", "123456", newUserTypeId);
        UserType oldType = new UserType(UUID.randomUUID(), "USER", UserType.OWNER);
        User requester = new User(requesterId, "Owner", "owner@test.com", "senha", new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER));
        User existing = new User(targetUserId, "John", "john@test.com", "senha", oldType);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(userGateway.findById(targetUserId)).thenReturn(Optional.of(existing));
        when(userTypeGateway.findById(newUserTypeId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(dto, requesterId));
        assertEquals("UserType not found for id: " + newUserTypeId, ex.getMessage());
    }

    @Test
    @DisplayName("Deve manter userType existente quando inputDTO.userTypeId for nulo")
    void deveManterUserTypeExistenteQuandoUserTypeIdForNulo() {
        UserType oldType = new UserType(UUID.randomUUID(), "USER", UserType.OWNER);
        User requester = new User(requesterId, "Owner", "owner@test.com", "senha", new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER));
        User existing = new User(targetUserId, "John", "john@test.com", "senha", oldType);

        UpdateUserInputDTO dto = new UpdateUserInputDTO(targetUserId, "Jane", "jane@test.com", "123456", null);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(userGateway.findById(targetUserId)).thenReturn(Optional.of(existing));
        when(userGateway.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updated = useCase.execute(dto, requesterId);

        assertEquals(oldType, updated.getUserType());
        verify(userTypeGateway, never()).findById(any());
    }

    @Test
    @DisplayName("updateValue String deve manter valor atual quando novo valor for nulo")
    void updateValueString_deveManterValorAtualQuandoNovoForNulo() {
        UserType type = new UserType(UUID.randomUUID(), "USER", UserType.OWNER);
        User requester = new User(requesterId, "Owner", "owner@test.com", "senha", new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER));
        User existing = new User(targetUserId, "John", "john@test.com", "senha", type);

        UpdateUserInputDTO dto = new UpdateUserInputDTO(targetUserId, null, null, null, null);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(userGateway.findById(targetUserId)).thenReturn(Optional.of(existing));
        when(userGateway.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updated = useCase.execute(dto, requesterId);

        assertEquals("John", updated.getName());
        assertEquals("john@test.com", updated.getEmail());
        assertEquals("senha", updated.getPasswordHash());
    }
}
