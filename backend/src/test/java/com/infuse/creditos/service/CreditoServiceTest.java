package com.infuse.creditos.service;

import com.infuse.creditos.domain.Credito;
import com.infuse.creditos.repository.CreditoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários para CreditoService")
class CreditoServiceTest {

    @Mock
    CreditoRepository repository;

    @InjectMocks
    CreditoService service;

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
    @DisplayName("Busca por número de crédito")
    class BuscaPorNumeroCredito {
        @Test
        @DisplayName("Deve retornar crédito ao buscar por número de crédito existente")
        void deveRetornarCreditoExistente() {
            Credito credito = novoCredito("123456", "7891011");
            when(repository.findByNumeroCredito("123456")).thenReturn(Optional.of(credito));
            Optional<Credito> result = service.buscarPorNumeroCredito("123456");
            assertTrue(result.isPresent());
            assertEquals("7891011", result.get().getNumeroNfse());
        }

        @Test
        @DisplayName("Deve lançar exceção ao buscar crédito inexistente")
        void deveLancarExcecaoCreditoInexistente() {
            when(repository.findByNumeroCredito("inexistente")).thenReturn(Optional.empty());
            Optional<Credito> result = service.buscarPorNumeroCredito("inexistente");
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("Busca por número de NFS-e")
    class BuscaPorNumeroNfse {
        @Test
        @DisplayName("Deve retornar lista ao buscar por número de NFS-e")
        void deveRetornarListaPorNfse() {
            List<Credito> lista = Arrays.asList(novoCredito("1", "NFS1"), novoCredito("2", "NFS1"));
            when(repository.findAllByNumeroNfse("NFS1")).thenReturn(lista);
            List<Credito> result = service.buscarPorNumeroNfse("NFS1");
            assertEquals(2, result.size());
        }
    }

    @Nested
    @DisplayName("Validação de dados inválidos")
    class ValidacaoDadosInvalidos {
        @Test
        @DisplayName("Deve tratar casos de dados inválidos")
        void deveTratarDadosInvalidos() {
            when(repository.findByNumeroCredito(null)).thenReturn(Optional.empty());
            Optional<Credito> result = service.buscarPorNumeroCredito(null);
            assertTrue(result.isEmpty());
        }
    }
}
