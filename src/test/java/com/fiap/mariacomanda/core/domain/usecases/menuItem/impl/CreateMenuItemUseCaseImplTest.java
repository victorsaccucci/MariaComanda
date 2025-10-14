package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.exception.EntityNotFoundException;
import com.fiap.mariacomanda.core.domain.exception.ValidationException;
import com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.core.mapper.MenuItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateMenuItemUseCaseImplTest {

    private MenuItemGateway menuItemGateway;
    private RestaurantGateway restaurantGateway;
    private UserGateway userGateway;
    private MenuItemMapper menuItemMapper;
    private CreateMenuItemUseCaseImpl useCase;

    private UUID requesterUserId;
    private UserType ownerType;
    private CreateMenuItemInputDTO validInputDTO;
    private UUID restaurantId;

    @BeforeEach
    void setUp() {
        menuItemGateway = mock(MenuItemGateway.class);
        restaurantGateway = mock(RestaurantGateway.class);
        userGateway = mock(UserGateway.class);
        menuItemMapper = mock(MenuItemMapper.class);
        useCase = new CreateMenuItemUseCaseImpl(menuItemGateway, restaurantGateway, userGateway, menuItemMapper);

        requesterUserId = UUID.randomUUID();
        ownerType = new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER);
        restaurantId = UUID.randomUUID();

        validInputDTO = new CreateMenuItemInputDTO(
                "Pizza Calabresa",
                "Pizza deliciosa",
                new BigDecimal("45.00"),
                restaurantId,
                true,
                "/images/pizza.jpg"
        );
    }

    @Test
    @DisplayName("Deve criar MenuItem com sucesso quando todos os dados forem válidos")
    void deveCriarMenuItemComSucesso() {
        User requester = new User(requesterUserId, "Dono", "dono@email.com", "senha", ownerType);
        Restaurant restaurant = new Restaurant(restaurantId, "Restaurante X", "Rua Y", "Italiana", "10:00-22:00", requesterUserId);
        MenuItem domain = new MenuItem(UUID.randomUUID(), restaurantId, validInputDTO.name(), validInputDTO.description(), validInputDTO.price(), validInputDTO.dineInOnly(), validInputDTO.photoPath());

        when(userGateway.findById(requesterUserId)).thenReturn(Optional.of(requester));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuItemMapper.toDomain(validInputDTO)).thenReturn(domain);
        when(menuItemGateway.save(domain)).thenReturn(domain);

        MenuItem result = useCase.execute(validInputDTO, requesterUserId);

        assertNotNull(result);
        assertEquals(validInputDTO.name(), result.getName());
        verify(userGateway).findById(requesterUserId);
        verify(restaurantGateway).findById(restaurantId);
        verify(menuItemMapper).toDomain(validInputDTO);
        verify(menuItemGateway).save(domain);
    }

    @Test
    @DisplayName("Deve lançar exceção quando inputDTO for nulo")
    void deveLancarExcecaoQuandoInputNulo() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> useCase.execute(null, requesterUserId));
        assertEquals("com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando requester não existir")
    void deveLancarExcecaoQuandoRequesterNaoExistir() {
        when(userGateway.findById(requesterUserId)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> useCase.execute(validInputDTO, requesterUserId));
        assertEquals("Requester user not found not found with identifier: " + requesterUserId, ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando restaurantId não existir")
    void deveLancarExcecaoQuandoRestaurantNaoExistir() {
        User requester = new User(requesterUserId, "Dono", "dono@email.com", "senha", ownerType);

        when(userGateway.findById(requesterUserId)).thenReturn(Optional.of(requester));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> useCase.execute(validInputDTO, requesterUserId));
        assertEquals("Restaurant not found not found with identifier: " + restaurantId, ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando name for vazio ou nulo")
    void deveLancarExcecaoQuandoNomeInvalido() {
        User requester = new User(requesterUserId, "Dono", "dono@email.com", "senha", ownerType);
        when(userGateway.findById(requesterUserId)).thenReturn(Optional.of(requester));

        // Nome nulo
        CreateMenuItemInputDTO inputNulo = new CreateMenuItemInputDTO(null, "Desc", BigDecimal.TEN, restaurantId, true, "/images/test.jpg");
        ValidationException exNulo = assertThrows(ValidationException.class,
                () -> useCase.execute(inputNulo, requesterUserId));
        assertEquals("Menu item name cannot be null", exNulo.getMessage());

        // Nome vazio
        CreateMenuItemInputDTO inputVazio = new CreateMenuItemInputDTO(" ", "Desc", BigDecimal.TEN, restaurantId, true, "/images/test.jpg");
        ValidationException exVazio = assertThrows(ValidationException.class,
                () -> useCase.execute(inputVazio, requesterUserId));
        assertEquals("Menu item name cannot be empty", exVazio.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando description for vazio ou nulo")
    void deveLancarExcecaoQuandoDescricaoInvalida() {
        User requester = new User(requesterUserId, "Dono", "dono@email.com", "senha", ownerType);
        when(userGateway.findById(requesterUserId)).thenReturn(Optional.of(requester));

        // Descrição nula
        CreateMenuItemInputDTO inputNulo = new CreateMenuItemInputDTO("Nome", null, BigDecimal.TEN, restaurantId, true, "/images/test.jpg");
        ValidationException exNulo = assertThrows(ValidationException.class,
                () -> useCase.execute(inputNulo, requesterUserId));
        assertEquals("Menu item description cannot be null", exNulo.getMessage());

        // Descrição vazia
        CreateMenuItemInputDTO inputVazio = new CreateMenuItemInputDTO("Nome", "  ", BigDecimal.TEN, restaurantId, true, "/images/test.jpg");
        IllegalArgumentException exVazio = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(inputVazio, requesterUserId));
        assertEquals("Menu item description cannot be empty", exVazio.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando photoPath for inválido")
    void deveLancarExcecaoQuandoPhotoPathInvalido() {
        User requester = new User(requesterUserId, "Dono", "dono@email.com", "senha", ownerType);
        when(userGateway.findById(requesterUserId)).thenReturn(Optional.of(requester));

        // Path vazio
        CreateMenuItemInputDTO inputVazio = new CreateMenuItemInputDTO("Nome", "Desc", BigDecimal.TEN, restaurantId, true, " ");
        ValidationException exVazio = assertThrows(ValidationException.class,
                () -> useCase.execute(inputVazio, requesterUserId));
        assertEquals("Photo path cannot be empty", exVazio.getMessage());

        // Path inválido
        CreateMenuItemInputDTO inputInvalido = new CreateMenuItemInputDTO("Nome", "Desc", BigDecimal.TEN, restaurantId, true, "/invalid/photo.pdf");
        IllegalArgumentException exInvalido = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(inputInvalido, requesterUserId));
        assertEquals("Photo path must be a valid image path (e.g., /images/photo.jpg)", exInvalido.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando price for zero ou negativo")
    void deveLancarExcecaoQuandoPrecoInvalido() {
        User requester = new User(requesterUserId, "Dono", "dono@email.com", "senha", ownerType);
        when(userGateway.findById(requesterUserId)).thenReturn(Optional.of(requester));

        CreateMenuItemInputDTO inputZero = new CreateMenuItemInputDTO("Nome", "Desc", BigDecimal.ZERO, restaurantId, true, "/images/test.jpg");
        ValidationException exZero = assertThrows(ValidationException.class,
                () -> useCase.execute(inputZero, requesterUserId));
        assertEquals("Menu item price must be greater than zero", exZero.getMessage());

        CreateMenuItemInputDTO inputNegativo = new CreateMenuItemInputDTO("Nome", "Desc", new BigDecimal("-10"), restaurantId, true, "/images/test.jpg");
        ValidationException exNegativo = assertThrows(ValidationException.class,
                () -> useCase.execute(inputNegativo, requesterUserId));
        assertEquals("Menu item price must be greater than zero", exNegativo.getMessage());
    }




}
