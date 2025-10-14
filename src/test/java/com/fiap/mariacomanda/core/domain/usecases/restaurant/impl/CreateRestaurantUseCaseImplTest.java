package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.exception.ValidationException;
import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.mapper.RestaurantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateRestaurantUseCaseImplTest {

    private RestaurantGateway restaurantGateway;
    private UserGateway userGateway;
    private RestaurantMapper restaurantMapper;
    private CreateRestaurantUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        restaurantGateway = mock(RestaurantGateway.class);
        userGateway = mock(UserGateway.class);
        restaurantMapper = mock(RestaurantMapper.class);
        useCase = new CreateRestaurantUseCaseImpl(restaurantGateway, userGateway, restaurantMapper);
    }

    @Test
    @DisplayName("Deve criar restaurante quando requester for OWNER")
    void deveCriarRestauranteQuandoRequesterForOwner() {
        // Arrange
        UUID requesterId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();

        CreateRestaurantInputDTO inputDTO = mock(CreateRestaurantInputDTO.class);
        when(inputDTO.ownerUserId()).thenReturn(ownerId);

        // Mock do requester OWNER
        UserType ownerType = mock(UserType.class);
        when(ownerType.isOwner()).thenReturn(true);

        User requester = mock(User.class);
        when(requester.getUserType()).thenReturn(ownerType);
        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));

        // Mock do ownerUser (mesmo que requester neste caso)
        User ownerUser = mock(User.class);
        when(ownerUser.getUserType()).thenReturn(ownerType);
        when(userGateway.findById(ownerId)).thenReturn(Optional.of(ownerUser));

        // Mock do mapper e do gateway
        Restaurant restaurantDomain = mock(Restaurant.class);
        when(restaurantMapper.toDomain(inputDTO)).thenReturn(restaurantDomain);

        Restaurant restaurantReturned = mock(Restaurant.class);
        when(restaurantGateway.save(restaurantDomain)).thenReturn(restaurantReturned);

        // Act
        Restaurant result = useCase.execute(inputDTO, requesterId);

        // Assert
        assertEquals(restaurantReturned, result);
        verify(userGateway).findById(requesterId);
        verify(userGateway).findById(ownerId);
        verify(restaurantMapper).toDomain(inputDTO);
        verify(restaurantGateway).save(restaurantDomain);
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException se inputDTO for nulo")
    void deveLancarExcecaoSeInputDTONulo() {
        UUID requesterId = UUID.randomUUID();
        ValidationException exception = assertThrows(ValidationException.class,
                () -> useCase.execute(null, requesterId)
        );
        assertEquals("com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException se requesterUserId for nulo")
    void deveLancarExcecaoSeRequesterUserIdForNulo() {
        CreateRestaurantInputDTO inputDTO = mock(CreateRestaurantInputDTO.class);
        ValidationException exception = assertThrows(ValidationException.class,
                () -> useCase.execute(inputDTO, null)
        );
        assertEquals("Requester user ID cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException se usuário não encontrado")
    void deveLancarExcecaoSeUsuarioNaoEncontrado() {
        UUID requesterId = UUID.randomUUID();
        CreateRestaurantInputDTO inputDTO = mock(CreateRestaurantInputDTO.class);
        when(userGateway.findById(requesterId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(inputDTO, requesterId)
        );
        assertEquals("Requester user not found", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar IllegalStateException se usuário não for OWNER")
    void deveLancarExcecaoSeUsuarioNaoForOwner() {
        UUID requesterId = UUID.randomUUID();
        CreateRestaurantInputDTO inputDTO = mock(CreateRestaurantInputDTO.class);

        UserType userTypeNaoOwner = mock(UserType.class);
        when(userTypeNaoOwner.isOwner()).thenReturn(false);

        User requester = mock(User.class);
        when(requester.getUserType()).thenReturn(userTypeNaoOwner);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));

        Exception exception = assertThrows(IllegalStateException.class,
                () -> useCase.execute(inputDTO, requesterId)
        );
        assertEquals("Only OWNER users can create restaurants", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar IllegalStateException se userType do requester for nulo")
    void deveLancarExcecaoSeUserTypeDoRequesterForNulo() {
        UUID requesterId = UUID.randomUUID();
        CreateRestaurantInputDTO inputDTO = mock(CreateRestaurantInputDTO.class);

        User requester = mock(User.class);
        when(requester.getUserType()).thenReturn(null);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));

        Exception exception = assertThrows(IllegalStateException.class,
                () -> useCase.execute(inputDTO, requesterId)
        );
        assertEquals("Only OWNER users can create restaurants", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar requester quando ownerUserId é igual ao requester")
    void resolveOwnerUserOwnerIsRequester() {
        UUID requesterId = UUID.randomUUID();

        CreateRestaurantInputDTO inputDTO = mock(CreateRestaurantInputDTO.class);
        when(inputDTO.ownerUserId()).thenReturn(requesterId);

        UserType ownerType = new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER);
        User requester = new User(requesterId, "Requester", "requester@test.com", "pass", ownerType);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));

        Restaurant restaurantMock = mock(Restaurant.class);
        when(restaurantMapper.toDomain(inputDTO)).thenReturn(restaurantMock);
        when(restaurantGateway.save(restaurantMock)).thenReturn(restaurantMock);


        Restaurant result = useCase.execute(inputDTO, requesterId);


        assertNotNull(result);
        verify(userGateway).findById(requesterId);
        verify(restaurantMapper).toDomain(inputDTO);
        verify(restaurantGateway).save(restaurantMock);
    }

    @Test
    @DisplayName("Deve buscar outro usuário quando ownerUserId é diferente do requester")
    void resolveOwnerUserOwnerIsDifferent() {
        UUID requesterId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        UserType customerType = new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER);

        User requester = new User(requesterId, "Requester", "requester@test.com", "pass", customerType);

        User ownerUser = new User(ownerId, "Owner", "owner@test.com", "pass", customerType);

        CreateRestaurantInputDTO inputDTO = mock(CreateRestaurantInputDTO.class);
        when(inputDTO.ownerUserId()).thenReturn(ownerId);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(userGateway.findById(ownerId)).thenReturn(Optional.of(ownerUser));

        Restaurant restaurantMock = mock(Restaurant.class);
        when(restaurantMapper.toDomain(inputDTO)).thenReturn(restaurantMock);
        when(restaurantGateway.save(restaurantMock)).thenReturn(restaurantMock);

        Restaurant result = useCase.execute(inputDTO, requesterId);

        assertNotNull(result);
        verify(userGateway).findById(ownerId);
        verify(userGateway).findById(requesterId);
        verify(restaurantMapper).toDomain(inputDTO);
        verify(restaurantGateway).save(restaurantMock);
    }

    @Test
    @DisplayName("Deve lançar exceção quando ownerUserId não é requester e não existe no gateway")
    void resolveOwnerUserOwnerNotFound() {
        UUID ownerId = UUID.randomUUID();
        UUID requesterId = UUID.randomUUID();

        CreateRestaurantInputDTO inputDTO = mock(CreateRestaurantInputDTO.class);
        when(inputDTO.ownerUserId()).thenReturn(ownerId);

        UserType ownerType = new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER);
        User requester = new User(requesterId, "Requester", "requester@test.com", "pass", ownerType);
        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));

        when(userGateway.findById(ownerId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(inputDTO, requesterId);
        });

        assertEquals("Owner user not found", exception.getMessage());
        verify(userGateway).findById(ownerId);
    }

}