package com.fiap.mariacomanda.core.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTypeTest {

    @Test
    @DisplayName("Construtor UserType(name, subType) deve criar objeto válido")
    void construtorSemId_deveCriarObjetoValido() {
        UserType userType = new UserType("Example Name", UserType.CUSTOMER);

        assertEquals("Example Name", userType.getName());
        assertEquals(UserType.CUSTOMER, userType.getSubType());
        assertNull(userType.getId(), "ID deve ser nulo quando não informado");
    }

    @Test
    @DisplayName("createCustomer deve criar UserType válido com subType CUSTOMER")
    void createCustomer_deveCriarUserTypeValido() {
        UUID id = UUID.randomUUID();
        String name = "Restaurante Teste";

        UserType userType = UserType.createCustomer(id, name);

        assertNotNull(userType, "UserType não pode ser nulo");
        assertEquals(id, userType.getId(), "ID deve ser igual ao fornecido");
        assertEquals(name, userType.getName(), "Nome deve ser igual ao fornecido");
        assertEquals(UserType.CUSTOMER, userType.getSubType(), "subType deve ser CUSTOMER");
    }

    @Test
    @DisplayName("createOwner deve criar UserType válido com subType OWNER")
    void createOwner_deveCriarUserTypeValido() {
        UUID id = UUID.randomUUID();
        String name = "Restaurante Proprietário";

        UserType userType = UserType.createOwner(id, name);

        assertNotNull(userType, "UserType não pode ser nulo");
        assertEquals(id, userType.getId(), "ID deve ser igual ao fornecido");
        assertEquals(name, userType.getName(), "Nome deve ser igual ao fornecido");
        assertEquals(UserType.OWNER, userType.getSubType(), "subType deve ser OWNER");
    }

    @Test
    @DisplayName("Deve lançar exceção quando name for nulo")
    void validateName_deveLancarExcecaoQuandoNameForNulo() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new UserType(UUID.randomUUID(), null, UserType.CUSTOMER)
        );
        assertEquals("UserType name cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando name for vazio")
    void validateName_deveLancarExcecaoQuandoNameForVazio() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new UserType(UUID.randomUUID(), "   ", UserType.CUSTOMER)
        );
        assertEquals("UserType name cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando name exceder 120 caracteres")
    void validateName_deveLancarExcecaoQuandoNameExceder120Caracteres() {
        String longName = "a".repeat(121);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new UserType(UUID.randomUUID(), longName, UserType.CUSTOMER)
        );
        assertEquals("UserType name cannot exceed 120 characters", exception.getMessage());
    }

    @Test
    @DisplayName("Deve aceitar name válido com menos de 120 caracteres")
    void validateName_deveAceitarNameValido() {
        String validName = "Nome válido";
        UserType userType = new UserType(UUID.randomUUID(), validName, UserType.CUSTOMER);
        assertEquals(validName, userType.getName());
    }

    @Test
    @DisplayName("Deve remover espaços em branco ao validar name")
    void validateName_deveRemoverEspacos() {
        String nameWithSpaces = "  Nome com espaço  ";
        UserType userType = new UserType(UUID.randomUUID(), nameWithSpaces, UserType.CUSTOMER);
        assertEquals("Nome com espaço", userType.getName());
    }

    @Test
    @DisplayName("Deve lançar exceção quando id for nulo")
    void validateId_deveLancarExcecaoQuandoIdForNulo() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new UserType(null, "Owner", UserType.OWNER)
        );
        assertEquals("UserType ID cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Deve aceitar id válido")
    void validateId_deveAceitarIdValido() {
        UUID validId = UUID.randomUUID();
        UserType userType = new UserType(validId, "Customer", UserType.CUSTOMER);
        assertEquals(validId, userType.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando subType for nulo")
    void validateSubType_deveLancarExcecaoQuandoForNulo() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new UserType(UUID.randomUUID(), "Owner", null)
        );
        assertEquals("UserType subType cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando subType for vazio")
    void validateSubType_deveLancarExcecaoQuandoForVazio() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new UserType(UUID.randomUUID(), "Owner", "  ")
        );
        assertEquals("UserType subType cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando subType for inválido")
    void validateSubType_deveLancarExcecaoQuandoForInvalido() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new UserType(UUID.randomUUID(), "Owner", "INVALID")
        );
        assertTrue(exception.getMessage().startsWith("Invalid user subType"));
    }

    @Test
    @DisplayName("Deve aceitar subType CUSTOMER")
    void validateSubType_deveAceitarCustomer() {
        UserType userType = new UserType(UUID.randomUUID(), "Customer", UserType.CUSTOMER);
        assertEquals(UserType.CUSTOMER, userType.getSubType());
    }

    @Test
    @DisplayName("Deve aceitar subType OWNER")
    void validateSubType_deveAceitarOwner() {
        UserType userType = new UserType(UUID.randomUUID(), "Owner", UserType.OWNER);
        assertEquals(UserType.OWNER, userType.getSubType());
    }

    @Test
    @DisplayName("isCustomer deve retornar true para CUSTOMER")
    void isCustomer_deveRetornarTrue() {
        UserType customerType = new UserType(UUID.randomUUID(), "Customer Name", UserType.CUSTOMER);
        assertTrue(customerType.isCustomer());
        assertFalse(customerType.isOwner());
    }

    @Test
    @DisplayName("isOwner deve retornar true para OWNER")
    void isOwner_deveRetornarTrue() {
        UserType ownerType = new UserType(UUID.randomUUID(), "Owner Name", UserType.OWNER);
        assertTrue(ownerType.isOwner());
        assertFalse(ownerType.isCustomer());
    }

    @Test
    @DisplayName("setName deve alterar nome corretamente")
    void setName_deveAlterarNome() {
        UserType userType = new UserType(UUID.randomUUID(), "Old Name", UserType.CUSTOMER);
        userType.setName("New Name");
        assertEquals("New Name", userType.getName());
    }

    @Test
    @DisplayName("setName deve lançar exceção quando nome for nulo")
    void setName_deveLancarExcecaoQuandoNomeForNulo() {
        UserType userType = new UserType(UUID.randomUUID(), "Old Name", UserType.CUSTOMER);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userType.setName(null)
        );
        assertEquals("UserType name cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("setName deve lançar exceção quando nome for vazio")
    void setName_deveLancarExcecaoQuandoNomeForVazio() {
        UserType userType = new UserType(UUID.randomUUID(), "Old Name", UserType.CUSTOMER);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userType.setName(" ")
        );
        assertEquals("UserType name cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("setSubType deve alterar corretamente")
    void setSubType_deveAlterarSubType() {
        UserType userType = new UserType(UUID.randomUUID(), "Name", UserType.CUSTOMER);
        userType.setSubType(UserType.OWNER);
        assertEquals(UserType.OWNER, userType.getSubType());
        assertTrue(userType.isOwner());
    }

    @Test
    @DisplayName("setSubType deve lançar exceção quando for nulo")
    void setSubType_deveLancarExcecaoQuandoNulo() {
        UserType userType = new UserType(UUID.randomUUID(), "Name", UserType.CUSTOMER);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userType.setSubType(null)
        );
        assertEquals("UserType subType cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("setSubType deve lançar exceção quando for inválido")
    void setSubType_deveLancarExcecaoQuandoInvalido() {
        UserType userType = new UserType(UUID.randomUUID(), "Name", UserType.CUSTOMER);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userType.setSubType("INVALID")
        );
        assertTrue(exception.getMessage().startsWith("Invalid user subType"));
    }

}