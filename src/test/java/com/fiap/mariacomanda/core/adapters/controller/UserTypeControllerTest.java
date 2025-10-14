package com.fiap.mariacomanda.core.adapters.controller;

import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.exception.EntityNotFoundException;
import com.fiap.mariacomanda.core.domain.usecases.usertype.*;
import com.fiap.mariacomanda.core.dto.usertype.input.*;
import com.fiap.mariacomanda.core.dto.usertype.output.CreateUserTypeOutputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.GetUserTypeOutputDTO;
import com.fiap.mariacomanda.core.mapper.UserTypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTypeControllerTest {

    private CreateUserTypeUseCase createUseCase;
    private GetUserTypeUseCase getUseCase;
    private ListUserTypeUseCase listUseCase;
    private UpdateUserTypeUseCase updateUseCase;
    private DeleteUserTypeUseCase deleteUseCase;
    private UserTypeMapper userTypeMapper;
    private UserTypeController controller;

    private UUID userTypeId;
    private UUID requesterId;

    @BeforeEach
    void setUp() {
        createUseCase = mock(CreateUserTypeUseCase.class);
        getUseCase = mock(GetUserTypeUseCase.class);
        listUseCase = mock(ListUserTypeUseCase.class);
        updateUseCase = mock(UpdateUserTypeUseCase.class);
        deleteUseCase = mock(DeleteUserTypeUseCase.class);
        userTypeMapper = mock(UserTypeMapper.class);

        controller = new UserTypeController(
                createUseCase, getUseCase, listUseCase, updateUseCase, deleteUseCase, userTypeMapper
        );
        userTypeId = UUID.randomUUID();
        requesterId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve criar tipo de usuário com sucesso")
    void testCreateUserType() {
        CreateUserTypeInputDTO inputDTO = mock(CreateUserTypeInputDTO.class);
        UserType userType = mock(UserType.class);
        CreateUserTypeOutputDTO outputDTO = mock(CreateUserTypeOutputDTO.class);

        when(createUseCase.execute(inputDTO, requesterId)).thenReturn(userType);
        when(userTypeMapper.toCreateOutput(userType)).thenReturn(outputDTO);

        CreateUserTypeOutputDTO result = controller.create(inputDTO, requesterId);

        assertEquals(outputDTO, result);
        verify(createUseCase).execute(inputDTO, requesterId);
        verify(userTypeMapper).toCreateOutput(userType);
    }

    @Test
    @DisplayName("Deve retornar tipo de usuário por id")
    void testGetUserType_found() {
        GetUserTypeInputDTO inputDTO = mock(GetUserTypeInputDTO.class);
        UserType userType = mock(UserType.class);
        GetUserTypeOutputDTO expected = mock(GetUserTypeOutputDTO.class);

        when(inputDTO.id()).thenReturn(userTypeId);
        when(getUseCase.execute(userTypeId)).thenReturn(Optional.of(userType));
        when(userTypeMapper.toGetOutput(userType)).thenReturn(expected);

        GetUserTypeOutputDTO result = controller.get(inputDTO);

        assertEquals(expected, result);
        verify(getUseCase).execute(userTypeId);
        verify(userTypeMapper).toGetOutput(userType);
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando tipo de usuário não encontrado")
    void testGetUserType_notFound() {
        GetUserTypeInputDTO inputDTO = mock(GetUserTypeInputDTO.class);

        when(inputDTO.id()).thenReturn(userTypeId);
        when(getUseCase.execute(userTypeId)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> controller.get(inputDTO));
        assertTrue(ex.getMessage().toLowerCase().contains("user type not found"));
        verify(getUseCase).execute(userTypeId);
        verify(userTypeMapper, never()).toGetOutput(any());
    }

    @Test
    @DisplayName("Deve listar tipos de usuário com sucesso")
    void testListUserTypes() {
        ListUserTypeInputDTO inputDTO = mock(ListUserTypeInputDTO.class);
        List<UserType> userTypes = List.of(mock(UserType.class), mock(UserType.class));
        List<GetUserTypeOutputDTO> expectedList = List.of(mock(GetUserTypeOutputDTO.class), mock(GetUserTypeOutputDTO.class));

        when(inputDTO.page()).thenReturn(0);
        when(inputDTO.size()).thenReturn(10);

        when(listUseCase.execute(0, 10)).thenReturn(userTypes);
        when(userTypeMapper.toGetOutputList(userTypes)).thenReturn(expectedList);

        List<GetUserTypeOutputDTO> result = controller.list(inputDTO);

        assertEquals(expectedList, result);
        verify(listUseCase).execute(0, 10);
        verify(userTypeMapper).toGetOutputList(userTypes);
    }

    @Test
    @DisplayName("Deve atualizar tipo de usuário com sucesso")
    void testUpdateUserType() {
        UpdateUserTypeInputDTO inputDTO = mock(UpdateUserTypeInputDTO.class);
        UserType userType = mock(UserType.class);
        UserType updatedUserType = mock(UserType.class);
        GetUserTypeOutputDTO outputDTO = mock(GetUserTypeOutputDTO.class);

        when(userTypeMapper.toDomain(inputDTO)).thenReturn(userType);
        when(updateUseCase.execute(userType, requesterId)).thenReturn(updatedUserType);
        when(userTypeMapper.toGetOutput(updatedUserType)).thenReturn(outputDTO);

        GetUserTypeOutputDTO result = controller.update(inputDTO, requesterId);

        assertEquals(outputDTO, result);
        verify(userTypeMapper).toDomain(inputDTO);
        verify(updateUseCase).execute(userType, requesterId);
        verify(userTypeMapper).toGetOutput(updatedUserType);
    }

    @Test
    @DisplayName("Deve deletar tipo de usuário com sucesso")
    void testDeleteUserType() {
        DeleteUserTypeInputDTO inputDTO = mock(DeleteUserTypeInputDTO.class);
        when(inputDTO.id()).thenReturn(userTypeId);

        controller.delete(inputDTO, requesterId);

        verify(deleteUseCase).execute(userTypeId, requesterId);
    }
}