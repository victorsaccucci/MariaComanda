package com.fiap.mariacomanda.core.adapters.controller;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.exception.EntityNotFoundException;
import com.fiap.mariacomanda.core.domain.usecases.user.*;
import com.fiap.mariacomanda.core.dto.user.input.*;
import com.fiap.mariacomanda.core.dto.user.output.CreateUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.output.GetUserOutputDTO;
import com.fiap.mariacomanda.core.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private CreateUserUseCase createUseCase;
    private GetUserUseCase getUseCase;
    private ListUserUseCase listUseCase;
    private UpdateUserUseCase updateUseCase;
    private DeleteUserUseCase deleteUseCase;
    private UserMapper userMapper;
    private UserController controller;

    private UUID userId;
    private UUID requesterId;

    @BeforeEach
    void setUp() {
        createUseCase = mock(CreateUserUseCase.class);
        getUseCase = mock(GetUserUseCase.class);
        listUseCase = mock(ListUserUseCase.class);
        updateUseCase = mock(UpdateUserUseCase.class);
        deleteUseCase = mock(DeleteUserUseCase.class);
        userMapper = mock(UserMapper.class);

        controller = new UserController(
                createUseCase, getUseCase, listUseCase, updateUseCase, deleteUseCase, userMapper
        );
        userId = UUID.randomUUID();
        requesterId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void testCreateUser() {
        CreateUserInputDTO inputDTO = mock(CreateUserInputDTO.class);
        User user = mock(User.class);
        CreateUserOutputDTO outputDTO = mock(CreateUserOutputDTO.class);

        when(createUseCase.execute(inputDTO, requesterId)).thenReturn(user);
        when(userMapper.toCreateOutput(user)).thenReturn(outputDTO);

        CreateUserOutputDTO result = controller.create(inputDTO, requesterId);

        assertEquals(outputDTO, result);
        verify(createUseCase).execute(inputDTO, requesterId);
        verify(userMapper).toCreateOutput(user);
    }

    @Test
    @DisplayName("Deve retornar usuário por id")
    void testGetUser_found() {
        GetUserInputDTO inputDTO = mock(GetUserInputDTO.class);
        User user = mock(User.class);
        GetUserOutputDTO expected = mock(GetUserOutputDTO.class);

        when(inputDTO.id()).thenReturn(userId);
        when(getUseCase.execute(userId)).thenReturn(Optional.of(user));
        when(userMapper.toGetOutput(user)).thenReturn(expected);

        GetUserOutputDTO result = controller.get(inputDTO);

        assertEquals(expected, result);
        verify(getUseCase).execute(userId);
        verify(userMapper).toGetOutput(user);
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando usuário não encontrado")
    void testGetUser_notFound() {
        GetUserInputDTO inputDTO = mock(GetUserInputDTO.class);

        when(inputDTO.id()).thenReturn(userId);
        when(getUseCase.execute(userId)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> controller.get(inputDTO));
        assertTrue(ex.getMessage().toLowerCase().contains("user not found"));
        verify(getUseCase).execute(userId);
        verify(userMapper, never()).toGetOutput(any());
    }

    @Test
    @DisplayName("Deve listar usuários com sucesso")
    void testListUsers() {
        ListUserInputDTO inputDTO = mock(ListUserInputDTO.class);
        List<User> users = List.of(mock(User.class), mock(User.class));
        List<GetUserOutputDTO> expectedList = List.of(mock(GetUserOutputDTO.class), mock(GetUserOutputDTO.class));

        when(inputDTO.page()).thenReturn(0);
        when(inputDTO.size()).thenReturn(10);

        when(listUseCase.execute(0, 10)).thenReturn(users);
        when(userMapper.toGetOutputList(users)).thenReturn(expectedList);

        List<GetUserOutputDTO> result = controller.list(inputDTO);

        assertEquals(expectedList, result);
        verify(listUseCase).execute(0, 10);
        verify(userMapper).toGetOutputList(users);
    }

    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    void testUpdateUser() {
        UpdateUserInputDTO inputDTO = mock(UpdateUserInputDTO.class);
        User updatedUser = mock(User.class);
        GetUserOutputDTO outputDTO = mock(GetUserOutputDTO.class);

        when(updateUseCase.execute(inputDTO, requesterId)).thenReturn(updatedUser);
        when(userMapper.toGetOutput(updatedUser)).thenReturn(outputDTO);

        GetUserOutputDTO result = controller.update(inputDTO, requesterId);

        assertEquals(outputDTO, result);
        verify(updateUseCase).execute(inputDTO, requesterId);
        verify(userMapper).toGetOutput(updatedUser);
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void testDeleteUser() {
        DeleteUserInputDTO inputDTO = mock(DeleteUserInputDTO.class);
        when(inputDTO.id()).thenReturn(userId);

        controller.delete(inputDTO, requesterId);

        verify(deleteUseCase).execute(userId, requesterId);
    }
}