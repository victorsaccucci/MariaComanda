package com.fiap.mariacomanda.infrastructure.web.controller;

import com.fiap.mariacomanda.core.adapters.controller.UserTypeController;
import com.fiap.mariacomanda.core.dto.usertype.input.*;
import com.fiap.mariacomanda.core.dto.usertype.output.CreateUserTypeOutputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.GetUserTypeOutputDTO;
import com.fiap.mariacomanda.infrastructure.database.mapper.usertype.UserTypeJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserTypeJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateUserTypeJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserTypeApiControllerTest {

    @Mock
    private UserTypeController userTypeController;

    @Mock
    private UserTypeJsonMapper userTypeJsonMapper;

    @InjectMocks
    private UserTypeApiController userTypeApiController;

    private UUID requesterUserId;
    private UUID userTypeId;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        requesterUserId = UUID.randomUUID();
        userTypeId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve criar um tipo de usuário com sucesso")
    void deveCriarUserTypeComSucesso() {
        CreateUserTypeJson json = new CreateUserTypeJson();
        CreateUserTypeInputDTO inputDTO = new CreateUserTypeInputDTO("GERENTE", "Gerente");
        CreateUserTypeOutputDTO outputDTO = new CreateUserTypeOutputDTO(userTypeId);

        when(userTypeJsonMapper.toCreateInput(json)).thenReturn(inputDTO);
        when(userTypeController.create(inputDTO, requesterUserId)).thenReturn(outputDTO);

        CreateUserTypeOutputDTO result = userTypeApiController.createUser(requesterUserId, json);

        assertNotNull(result);
        assertEquals(userTypeId, result.id());
        verify(userTypeController).create(inputDTO, requesterUserId);
    }

    @Test
    @DisplayName("Deve retornar um tipo de usuário pelo ID")
    void deveBuscarUserTypePorId() {
        GetUserTypeOutputDTO outputDTO = new GetUserTypeOutputDTO(userTypeId, "ATENDENTE", "Atendente");

        when(userTypeController.get(any(GetUserTypeInputDTO.class))).thenReturn(outputDTO);

        GetUserTypeOutputDTO result = userTypeApiController.get(userTypeId);

        assertNotNull(result);
        assertEquals("ATENDENTE", result.name());
        verify(userTypeController).get(any(GetUserTypeInputDTO.class));
    }

    @Test
    @DisplayName("Deve listar tipos de usuários com paginação")
    void deveListarUserTypesComPaginacao() {
        GetUserTypeOutputDTO t1 = new GetUserTypeOutputDTO(UUID.randomUUID(), "ADMIN", "Administrador");
        GetUserTypeOutputDTO t2 = new GetUserTypeOutputDTO(UUID.randomUUID(), "GARÇOM", "Garçom");

        when(userTypeController.list(any(ListUserTypeInputDTO.class))).thenReturn(List.of(t1, t2));

        List<GetUserTypeOutputDTO> result = userTypeApiController.list(0, 10);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("ADMIN", result.get(0).name());
        verify(userTypeController).list(any(ListUserTypeInputDTO.class));
    }

    @Test
    @DisplayName("Deve atualizar um tipo de usuário com sucesso")
    void deveAtualizarUserTypeComSucesso() {
        UpdateUserTypeJson json = new UpdateUserTypeJson();
        UpdateUserTypeInputDTO inputDTO = new UpdateUserTypeInputDTO(userTypeId, "CAIXA", "Caixa");
        GetUserTypeOutputDTO outputDTO = new GetUserTypeOutputDTO(userTypeId, "CAIXA", "Caixa");

        when(userTypeJsonMapper.toUpdateInput(userTypeId, json)).thenReturn(inputDTO);
        when(userTypeController.update(inputDTO, requesterUserId)).thenReturn(outputDTO);

        GetUserTypeOutputDTO result = userTypeApiController.update(requesterUserId, userTypeId, json);

        assertNotNull(result);
        assertEquals("CAIXA", result.name());
        verify(userTypeController).update(inputDTO, requesterUserId);
    }

    @Test
    @DisplayName("Deve excluir um tipo de usuário com sucesso")
    void deveExcluirUserTypeComSucesso() {
        ResponseEntity<Void> response = userTypeApiController.delete(requesterUserId, userTypeId);

        assertEquals(204, response.getStatusCodeValue());
        verify(userTypeController).delete(any(DeleteUserTypeInputDTO.class), eq(requesterUserId));
    }
}
