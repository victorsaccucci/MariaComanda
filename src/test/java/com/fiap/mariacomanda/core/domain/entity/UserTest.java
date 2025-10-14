package com.fiap.mariacomanda.core.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTest {

    private static final UUID VALID_USERTYPE_ID = UUID.randomUUID();
    private static final String VALID_NAME = "Nome do Usuário";
    private static final String VALID_EMAIL = "usuario@dominio.com";
    private static final String VALID_PASSWORD_HASH = "hashsenha123";
    private static final UserType VALID_USERTYPE = new UserType(VALID_USERTYPE_ID, "Owner", UserType.OWNER);
    private final User user = new User("Nome", "email@test.com", "senha123",
            new UserType(UUID.randomUUID(), "OWNER", "OWNER"));

    private String invokeValidatePasswordHash(String passwordHash) throws Exception {
        Method method = User.class.getDeclaredMethod("validatePasswordHash", String.class);
        method.setAccessible(true);
        return (String) method.invoke(user, passwordHash);
    }

    @Test
    @DisplayName("validatePasswordHash deve retornar a senha quando válida")
    void validatePasswordHash_deveRetornarSenhaValida() throws Exception {
        String senha = "senha123";
        String result = invokeValidatePasswordHash(senha);
        assertEquals(senha, result);
    }

    @Test
    @DisplayName("validatePasswordHash deve retornar null quando senha for null")
    void validatePasswordHash_deveRetornarNullQuandoSenhaForNull() throws Exception {
        String result = invokeValidatePasswordHash(null);
        assertNull(result);
    }

    @Test
    @DisplayName("validatePasswordHash deve lançar exceção para string vazia")
    void validatePasswordHash_deveLancarExcecaoParaSenhaVazia() {
        assertThrows(InvocationTargetException.class, () -> invokeValidatePasswordHash(""));
        assertThrows(InvocationTargetException.class, () -> invokeValidatePasswordHash("   "));
    }

    @Test
    @DisplayName("getUserTypeId deve retornar null quando userType estiver nulo")
    void getUserTypeId_deveRetornarNullQuandoUserTypeForNulo() throws Exception {
        User user = new User(VALID_NAME, VALID_EMAIL, VALID_PASSWORD_HASH, VALID_USERTYPE);

        // Forçando userType para null via reflection para testar esse caminho
        var field = User.class.getDeclaredField("userType");
        field.setAccessible(true);
        field.set(user, null);

        assertNull(user.getUserTypeId());
    }

    @Test
    @DisplayName("getUserTypeId deve refletir atualização de userType")
    void getUserTypeId_deveRefletirUserTypeAtualizado() {
        User user = new User(VALID_NAME, VALID_EMAIL, VALID_PASSWORD_HASH, VALID_USERTYPE);
        UUID outroTipoId = UUID.randomUUID();
        UserType outroTipo = new UserType(outroTipoId, "Outro", UserType.CUSTOMER);

        user.setUserType(outroTipo);
        assertEquals(outroTipoId, user.getUserTypeId());
    }

    @Test
    @DisplayName("validateName deve lançar exceção para nome null ou vazio")
    void validateName_deveRecusarNuloOuVazio() {
        assertThrows(IllegalArgumentException.class, () -> new User(null, VALID_EMAIL, VALID_PASSWORD_HASH, VALID_USERTYPE));
        assertThrows(IllegalArgumentException.class, () -> new User("", VALID_EMAIL, VALID_PASSWORD_HASH, VALID_USERTYPE));
        assertThrows(IllegalArgumentException.class, () -> new User("    ", VALID_EMAIL, VALID_PASSWORD_HASH, VALID_USERTYPE));
    }

    @Test
    @DisplayName("validateName deve lançar exceção para nomes acima de 100 caracteres")
    void validateName_deveRecusarNomeMuitoComprido() {
        String longName = "A".repeat(101);
        assertThrows(IllegalArgumentException.class, () -> new User(longName, VALID_EMAIL, VALID_PASSWORD_HASH, VALID_USERTYPE));
    }

    @Test
    @DisplayName("validateEmail deve lançar exceção se email for null ou vazio")
    void validateEmail_deveLancarExcecaoSeEmailNuloOuVazio() {
        assertThrows(IllegalArgumentException.class, () -> new User(VALID_NAME, null, VALID_PASSWORD_HASH, VALID_USERTYPE));
        assertThrows(IllegalArgumentException.class, () -> new User(VALID_NAME, "", VALID_PASSWORD_HASH, VALID_USERTYPE));
        assertThrows(IllegalArgumentException.class, () -> new User(VALID_NAME, "   ", VALID_PASSWORD_HASH, VALID_USERTYPE));
    }

    @Test
    @DisplayName("validateEmail deve lançar exceção para formato inválido")
    void validateEmail_deveLancarExcecaoFormatoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new User(VALID_NAME, "sem-arroba-email.com", VALID_PASSWORD_HASH, VALID_USERTYPE));
        assertThrows(IllegalArgumentException.class, () -> new User(VALID_NAME, "usuario@dominio", VALID_PASSWORD_HASH, VALID_USERTYPE));
        assertThrows(IllegalArgumentException.class, () -> new User(VALID_NAME, "usuario@.com", VALID_PASSWORD_HASH, VALID_USERTYPE));
        assertThrows(IllegalArgumentException.class, () -> new User(VALID_NAME, "usuario@@dominio.com", VALID_PASSWORD_HASH, VALID_USERTYPE));
    }

    @Test
    @DisplayName("validateEmail deve lançar exceção quando e-mail exceder 255 caracteres")
    void validateEmail_deveLancarExcecaoQuandoEmailExceder255Caracteres() {
        String local = "a".repeat(64); // limite do RFC
        String dominio = "b".repeat(62) + "." + "c".repeat(61) + "." + "d".repeat(62) + ".com";
        String email256 = local + "@" + dominio;

        assertTrue(email256.length() > 255, "O e-mail deve exceder 255 caracteres para este teste");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new User(VALID_NAME, email256, VALID_PASSWORD_HASH, VALID_USERTYPE)
        );

        assertEquals("Email cannot exceed 255 characters", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando UserType for null")
    void validateUserType_deveLancarExcecaoQuandoUserTypeForNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new User("João", "joao@email.com", "1234", null)
        );

        assertEquals("UserType cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando UserType tiver ID nulo")
    void validateUserType_deveLancarExcecaoQuandoUserTypeIdForNulo() {
        UserType mockUserType = Mockito.mock(UserType.class);
        when(mockUserType.getId()).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new User(VALID_NAME, VALID_EMAIL, VALID_PASSWORD_HASH, mockUserType)
        );

        assertEquals("UserType ID cannot be null", exception.getMessage());
        verify(mockUserType, times(1)).getId();
    }

    @Test
    @DisplayName("validateId deve lançar exceção quando ID for nulo")
    void validateId_deveLancarExcecaoQuandoIdForNulo() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new User(null, VALID_NAME, VALID_EMAIL, VALID_PASSWORD_HASH, VALID_USERTYPE)
        );

        assertEquals("User ID cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("validateId deve aceitar UUID válido")
    void validateId_deveAceitarUUIDValido() {
        UUID idValido = UUID.randomUUID();
        User user = new User(idValido, VALID_NAME, VALID_EMAIL, VALID_PASSWORD_HASH, VALID_USERTYPE);

        assertEquals(idValido, user.getId());
    }

    @Test
    @DisplayName("setName deve atualizar o nome quando válido")
    void setName_deveAtualizarNomeQuandoValido() {
        User user = new User(VALID_NAME, VALID_EMAIL, VALID_PASSWORD_HASH, VALID_USERTYPE);

        String novoNome = "Maria";
        user.setName(novoNome);

        assertEquals(novoNome, user.getName());
    }

    @Test
    @DisplayName("setEmail deve atualizar o email quando válido")
    void setEmail_deveAtualizarEmailQuandoValido() {
        User user = new User(VALID_NAME, VALID_EMAIL, VALID_PASSWORD_HASH, VALID_USERTYPE);

        String novoEmail = "maria@email.com";
        user.setEmail(novoEmail);

        assertEquals(novoEmail, user.getEmail());
    }
}