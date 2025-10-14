package com.fiap.mariacomanda.infrastructure.web.controller;

import com.fiap.mariacomanda.core.adapters.controller.UserController;
import com.fiap.mariacomanda.core.dto.user.input.*;
import com.fiap.mariacomanda.core.dto.user.output.CreateUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.output.GetUserOutputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.GetUserTypeOutputDTO;
import com.fiap.mariacomanda.infrastructure.database.mapper.user.UserJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateUserJson;
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
import static org.mockito.Mockito.*;

class UserApiControllerTest {

    @Mock
    private UserController userController;

    @Mock
    private UserJsonMapper userJsonMapper;

    @InjectMocks
    private UserApiController userApiController;

    private UUID requesterUserId;
    private UUID userId;
    GetUserTypeOutputDTO userType = new GetUserTypeOutputDTO(UUID.randomUUID(), "OWNER", "Owner");

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        requesterUserId = UUID.randomUUID();
        userId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve criar um usuário com sucesso")
    void deveCriarUsuarioComSucesso() {
        CreateUserJson json = new CreateUserJson();
        CreateUserInputDTO inputDTO = new CreateUserInputDTO("João da Silva", "joao@email.com", "senha123", requesterUserId);
        CreateUserOutputDTO outputDTO = new CreateUserOutputDTO(userId, "Usuário criado com sucesso");

        when(userJsonMapper.toCreateInput(json)).thenReturn(inputDTO);
        when(userController.create(inputDTO, requesterUserId)).thenReturn(outputDTO);

        CreateUserOutputDTO result = userApiController.createUser(requesterUserId, json);

        assertNotNull(result);
        assertEquals(userId, result.id());
        verify(userController).create(inputDTO, requesterUserId);
    }

    @Test
    @DisplayName("Deve retornar um usuário pelo ID")
    void deveBuscarUsuarioPorId() {
        GetUserOutputDTO outputDTO = new GetUserOutputDTO(userId, "Maria Oliveira", "maria@email.com", userType);

        when(userController.get(any(GetUserInputDTO.class))).thenReturn(outputDTO);

        GetUserOutputDTO result = userApiController.get(userId);

        assertNotNull(result);
        assertEquals("Maria Oliveira", result.name());
        verify(userController).get(any(GetUserInputDTO.class));
    }

    @Test
    @DisplayName("Deve listar usuários com paginação")
    void deveListarUsuariosComPaginacao() {
        GetUserOutputDTO u1 = new GetUserOutputDTO(userId, "Carlos", "carlos@email.com", userType);
        GetUserOutputDTO u2 = new GetUserOutputDTO(userId, "Ana", "ana@email.com", userType);

        when(userController.list(any(ListUserInputDTO.class))).thenReturn(List.of(u1, u2));

        List<GetUserOutputDTO> result = userApiController.list(0, 10);

        assertEquals(2, result.size());
        verify(userController).list(any(ListUserInputDTO.class));
    }

    @Test
    @DisplayName("Deve atualizar um usuário com sucesso")
    void deveAtualizarUsuarioComSucesso() {
        UpdateUserJson json = new UpdateUserJson();
        UpdateUserInputDTO inputDTO = new UpdateUserInputDTO(userId, "João Atualizado", "joao@novoemail.com", "novaSenha123", requesterUserId);
        GetUserOutputDTO outputDTO = new GetUserOutputDTO(userId, "João Atualizado", "joao@novoemail.com", userType);

        when(userJsonMapper.toUpdateInput(userId, json)).thenReturn(inputDTO);
        when(userController.update(inputDTO, requesterUserId)).thenReturn(outputDTO);

        GetUserOutputDTO result = userApiController.update(requesterUserId, userId, json);

        assertNotNull(result);
        assertEquals("João Atualizado", result.name());
        verify(userController).update(inputDTO, requesterUserId);
    }

    @Test
    @DisplayName("Deve excluir um usuário com sucesso")
    void deveExcluirUsuarioComSucesso() {
        ResponseEntity<Void> response = userApiController.delete(requesterUserId, userId);

        assertEquals(204, response.getStatusCodeValue());
        verify(userController).delete(any(DeleteUserInputDTO.class), eq(requesterUserId));
    }
}
