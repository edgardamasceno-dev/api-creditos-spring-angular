package com.infuse.creditos.repository;

import com.infuse.creditos.domain.Credito;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes de integração para CreditoRepository")
class CreditoRepositoryIT {

    @Autowired
    CreditoRepository repository;

    private Credito novoCredito(String numeroCredito, String numeroNfse) {
        Credito c = new Credito();
        c.setNumeroCredito(numeroCredito);
        c.setNumeroNfse(numeroNfse);
        c.setDataConstituicao(LocalDate.of(2024, 2, 25));
        c.setValorIssqn(new BigDecimal("1500.75"));
        c.setTipoCredito("ISSQN");
        c.setSimplesNacional(true);
        c.setAliquota(new BigDecimal("5.0"));
        c.setValorFaturado(new BigDecimal("30000.00"));
        c.setValorDeducao(new BigDecimal("5000.00"));
        c.setBaseCalculo(new BigDecimal("25000.00"));
        return c;
    }

    @Nested
    @DisplayName("Persistência e recuperação")
    class Persistencia {
        @Test
        @DisplayName("Deve salvar e recuperar entidade Credito")
        void deveSalvarERecuperarCredito() {
            Credito credito = novoCredito("123456", "7891011");
            Credito salvo = repository.save(credito);
            Optional<Credito> encontrado = repository.findById(salvo.getId());
            assertTrue(encontrado.isPresent());
            assertEquals("123456", encontrado.get().getNumeroCredito());
        }
    }

    @Nested
    @DisplayName("Consultas customizadas")
    class Consultas {
        @Test
        @DisplayName("Deve buscar por número de crédito")
        void deveBuscarPorNumeroCredito() {
            Credito credito = repository.save(novoCredito("999999", "8888888"));
            Optional<Credito> encontrado = repository.findByNumeroCredito("999999");
            assertTrue(encontrado.isPresent());
            assertEquals("8888888", encontrado.get().getNumeroNfse());
        }

        @Test
        @DisplayName("Deve buscar por número de NFS-e")
        void deveBuscarPorNumeroNfse() {
            repository.save(novoCredito("111111", "NFS123"));
            repository.save(novoCredito("222222", "NFS123"));
            List<Credito> lista = repository.findAllByNumeroNfse("NFS123");
            assertEquals(2, lista.size());
        }

        @Test
        @DisplayName("Deve retornar vazio para buscas inexistentes")
        void deveRetornarVazioParaBuscasInexistentes() {
            Optional<Credito> credito = repository.findByNumeroCredito("inexistente");
            assertTrue(credito.isEmpty());
            List<Credito> lista = repository.findAllByNumeroNfse("nfse-inexistente");
            assertTrue(lista.isEmpty());
        }
    }

    @Nested
    @DisplayName("Integridade dos dados")
    class Integridade {
        @Test
        @DisplayName("Deve garantir integridade dos dados persistidos")
        void deveGarantirIntegridadeDosDados() {
            Credito credito = novoCredito("333333", "4444444");
            Credito salvo = repository.save(credito);
            assertNotNull(salvo.getId());
            assertEquals("333333", salvo.getNumeroCredito());
            assertEquals("4444444", salvo.getNumeroNfse());
        }
    }
}
