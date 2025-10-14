package com.fiap.mariacomanda.infrastructure.web.controller;

import com.fiap.mariacomanda.core.adapters.controller.RestaurantController;
import com.fiap.mariacomanda.core.dto.restaurant.input.*;
import com.fiap.mariacomanda.core.dto.restaurant.output.CreateRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.GetRestaurantOutputDTO;
import com.fiap.mariacomanda.infrastructure.database.mapper.restaurant.RestaurantJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.CreateRestaurantJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateRestaurantJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestaurantApiControllerTest {

    @Mock
    private RestaurantController restaurantController;

    @Mock
    private RestaurantJsonMapper restaurantJsonMapper;

    @InjectMocks
    private RestaurantApiController restaurantApiController;

    private UUID requesterUserId;
    private UUID restaurantId;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        requesterUserId = UUID.randomUUID();
        restaurantId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve criar um restaurante com sucesso")
    void deveCriarRestauranteComSucesso() {
        CreateRestaurantJson json = new CreateRestaurantJson();
        CreateRestaurantInputDTO inputDTO = new CreateRestaurantInputDTO("Restaurante Bom Sabor", "Avenida Campanella - São Paulo",
                "Culinária Brasileira", "12:00", requesterUserId);
        CreateRestaurantOutputDTO outputDTO = new CreateRestaurantOutputDTO(restaurantId);

        when(restaurantJsonMapper.toCreateInput(json)).thenReturn(inputDTO);
        when(restaurantController.create(inputDTO, requesterUserId)).thenReturn(outputDTO);

        CreateRestaurantOutputDTO result = restaurantApiController.create(requesterUserId, json);

        assertNotNull(result);
        assertEquals(restaurantId, result.id());
        verify(restaurantController).create(inputDTO, requesterUserId);
    }

    @Test
    @DisplayName("Deve retornar um restaurante pelo ID")
    void deveBuscarRestaurantePorId() {
        GetRestaurantOutputDTO outputDTO = new GetRestaurantOutputDTO(
                restaurantId,
                "Cantina Italiana",
                "Itália",
                "Comida típica italiana", "20:00", requesterUserId
        );

        when(restaurantController.get(any(GetRestaurantInputDTO.class))).thenReturn(outputDTO);

        GetRestaurantOutputDTO result = restaurantApiController.get(restaurantId);

        assertNotNull(result);
        assertEquals("Cantina Italiana", result.name());
        verify(restaurantController).get(any(GetRestaurantInputDTO.class));
    }

    @Test
    @DisplayName("Deve listar restaurantes com paginação")
    void deveListarRestaurantesComPaginacao() {
        GetRestaurantOutputDTO r1 = new GetRestaurantOutputDTO(restaurantId, "Sushi Place", "Japão","Japonesa",
                "20:00", requesterUserId);
        GetRestaurantOutputDTO r2 = new GetRestaurantOutputDTO(restaurantId, "Churrascaria Gaúcha", "Brás", "Carnes",
                "18:00", requesterUserId);

        when(restaurantController.list(any(ListRestaurantsInputDTO.class))).thenReturn(List.of(r1, r2));

        List<GetRestaurantOutputDTO> result = restaurantApiController.list(0, 10);

        assertEquals(2, result.size());
        verify(restaurantController).list(any(ListRestaurantsInputDTO.class));
    }

    @Test
    @DisplayName("Deve atualizar um restaurante com sucesso")
    void deveAtualizarRestauranteComSucesso() {
        UpdateRestaurantJson json = new UpdateRestaurantJson();
        UpdateRestaurantInputDTO inputDTO = new UpdateRestaurantInputDTO(restaurantId, "Sushi Place", "Japão","Japonesa",
                "20:00", requesterUserId);
        GetRestaurantOutputDTO outputDTO = new GetRestaurantOutputDTO(restaurantId, "Sushi Place", "Japão","Japonesa",
                "20:00", requesterUserId);

        when(restaurantJsonMapper.toUpdateInput(restaurantId, json)).thenReturn(inputDTO);
        when(restaurantController.update(inputDTO, requesterUserId)).thenReturn(outputDTO);

        GetRestaurantOutputDTO result = restaurantApiController.update(requesterUserId, restaurantId, json);

        assertEquals("Sushi Place", result.name());
        verify(restaurantController).update(inputDTO, requesterUserId);
    }

    @Test
    @DisplayName("Deve excluir um restaurante com sucesso")
    void deveExcluirRestauranteComSucesso() {
        ResponseEntity<Void> response = restaurantApiController.delete(requesterUserId, restaurantId);

        assertEquals(204, response.getStatusCodeValue());
        verify(restaurantController).delete(any(DeleteRestaurantInputDTO.class), eq(requesterUserId));
    }
}
