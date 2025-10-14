package com.fiap.mariacomanda.core.mapper.impl;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.input.UpdateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.CreateRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.GetRestaurantOutputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantMapperImplTest {

    private RestaurantMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new RestaurantMapperImpl();
    }

    @Test
    @DisplayName("Deve mapear CreateRestaurantInputDTO para Restaurant corretamente")
    void deveMapearCreateRestaurantInputDTOParaRestaurant() {
        UUID ownerId = UUID.randomUUID();
        CreateRestaurantInputDTO dto = new CreateRestaurantInputDTO(
                "Restaurante Exemplo",
                "Rua Exemplo, 123",
                "Italiana",
                "10:00-22:00",
                ownerId
        );

        Restaurant domain = mapper.toDomain(dto);

        assertNull(domain.getId());
        assertEquals(dto.name(), domain.getName());
        assertEquals(dto.address(), domain.getAddress());
        assertEquals(dto.cuisineType(), domain.getCuisineTypeForDisplay());
        assertEquals(dto.openingHours(), domain.getOpeningHoursForDisplay().replace("Seg-Dom: ", ""));
        assertEquals(dto.ownerUserId(), domain.getOwnerUserId());
    }

    @Test
    @DisplayName("Deve mapear UpdateRestaurantInputDTO para Restaurant corretamente")
    void deveMapearUpdateRestaurantInputDTOParaRestaurant() {
        UUID restaurantId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        UpdateRestaurantInputDTO dto = new UpdateRestaurantInputDTO(
                restaurantId,
                "Restaurante Atualizado",
                "Rua Atualizada, 456",
                "Japonesa",
                "11:00-23:00",
                ownerId
        );

        Restaurant domain = mapper.toDomain(dto);

        assertEquals(dto.id(), domain.getId());
        assertEquals(dto.name(), domain.getName());
        assertEquals(dto.address(), domain.getAddress());
        assertEquals(dto.cuisineType(), domain.getCuisineTypeForDisplay());
        assertEquals(dto.openingHours(), domain.getOpeningHoursForDisplay().replace("Seg-Dom: ", ""));
        assertEquals(dto.ownerUserId(), domain.getOwnerUserId());
    }

    @Test
    @DisplayName("Deve mapear Restaurant para CreateRestaurantOutputDTO corretamente")
    void deveMapearRestaurantParaCreateRestaurantOutputDTO() {
        UUID restaurantId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(
                restaurantId,
                "Restaurante Teste",
                "Rua Teste, 789",
                "Brasileira",
                "09:00-21:00",
                ownerId
        );

        CreateRestaurantOutputDTO dto = mapper.toCreateOutput(restaurant);

        assertEquals(restaurant.getId(), dto.id());
    }

    @Test
    @DisplayName("Deve mapear Restaurant para GetRestaurantOutputDTO corretamente")
    void deveMapearRestaurantParaGetRestaurantOutputDTO() {
        UUID restaurantId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(
                restaurantId,
                "Restaurante Teste",
                "Rua Teste, 789",
                "Brasileira",
                "09:00-21:00",
                ownerId
        );

        GetRestaurantOutputDTO dto = mapper.toGetOutput(restaurant);

        assertEquals(restaurant.getId(), dto.id());
        assertEquals(restaurant.getName(), dto.name());
        assertEquals(restaurant.getAddress(), dto.address());
        assertEquals(restaurant.getCuisineTypeForDisplay(), dto.cuisineType());
        assertEquals(restaurant.getOpeningHoursForDisplay(), dto.openingHours());
        assertEquals(restaurant.getOwnerUserId(), dto.ownerUserId());
    }

    @Test
    @DisplayName("Deve mapear lista de Restaurant para lista de GetRestaurantOutputDTO corretamente")
    void deveMapearListaDeRestaurantParaListaDeGetRestaurantOutputDTO() {
        UUID ownerId = UUID.randomUUID();
        Restaurant r1 = new Restaurant(
                UUID.randomUUID(),
                "Restaurante A",
                "Endereço A",
                "Chinesa",
                "08:00-20:00",
                ownerId
        );

        Restaurant r2 = new Restaurant(
                UUID.randomUUID(),
                "Restaurante B",
                "Endereço B",
                "Mexicana",
                "09:00-21:00",
                ownerId
        );

        List<GetRestaurantOutputDTO> dtoList = mapper.toGetOutputList(List.of(r1, r2));

        assertEquals(2, dtoList.size());
        assertEquals(r1.getName(), dtoList.get(0).name());
        assertEquals(r2.getName(), dtoList.get(1).name());
    }
}
