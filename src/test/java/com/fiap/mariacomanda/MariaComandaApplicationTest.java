package com.fiap.mariacomanda;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class MariaComandaApplicationTest {

    @Test
    void main() {
        // Mock SpringApplication.run para evitar inicialização completa da aplicação
        try (MockedStatic<SpringApplication> mockedSpringApplication = Mockito.mockStatic(SpringApplication.class)) {
            // Executa o metodo main para cobertura
            MariaComandaApplication.main(new String[]{});
            
            // Verifica se SpringApplication.run foi chamado
            mockedSpringApplication.verify(() -> 
                SpringApplication.run(MariaComandaApplication.class, new String[]{})
            );
        }
    }

    @Test
    void testConstructor() {
        // Testa o construtor para cobertura completa
        MariaComandaApplication app = new MariaComandaApplication();
        assert app != null;
    }
}