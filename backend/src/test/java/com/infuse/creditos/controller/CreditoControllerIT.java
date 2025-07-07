package com.infuse.creditos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infuse.creditos.domain.Credito;
import com.infuse.creditos.repository.CreditoRepository;
import com.infuse.creditos.service.CreditoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Testes de integração para CreditoController")
class CreditoControllerIT {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    CreditoRepository repository;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        Credito c1 = new Credito();
        c1.setNumeroCredito("123456");
        c1.setNumeroNfse("7891011");
        c1.setDataConstituicao(LocalDate.of(2024, 2, 25));
        c1.setValorIssqn(new BigDecimal("1500.75"));
        c1.setTipoCredito("ISSQN");
        c1.setSimplesNacional(true);
        c1.setAliquota(new BigDecimal("5.0"));
        c1.setValorFaturado(new BigDecimal("30000.00"));
        c1.setValorDeducao(new BigDecimal("5000.00"));
        c1.setBaseCalculo(new BigDecimal("25000.00"));
        repository.save(c1);
    }

    @Nested
    @DisplayName("GET /api/creditos/{numeroNfse}")
    class GetPorNumeroNfse {
        @Test
        @DisplayName("Deve retornar 200 e lista de créditos para NFS-e existente")
        void deveRetornarListaCreditos() throws Exception {
            mockMvc.perform(get("/api/creditos/7891011"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].numeroCredito").value("123456"));
        }

        @Test
        @DisplayName("Deve retornar 404 para NFS-e inexistente")
        void deveRetornar404NfseInexistente() throws Exception {
            mockMvc.perform(get("/api/creditos/naoexiste"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Deve retornar múltiplos créditos para mesma NFS-e")
        void deveRetornarMultiplosCreditosPorNfse() throws Exception {
            Credito c2 = new Credito();
            c2.setNumeroCredito("789012");
            c2.setNumeroNfse("7891011");
            c2.setDataConstituicao(LocalDate.of(2024, 2, 26));
            c2.setValorIssqn(new BigDecimal("1200.50"));
            c2.setTipoCredito("ISSQN");
            c2.setSimplesNacional(false);
            c2.setAliquota(new BigDecimal("4.5"));
            c2.setValorFaturado(new BigDecimal("25000.00"));
            c2.setValorDeducao(new BigDecimal("4000.00"));
            c2.setBaseCalculo(new BigDecimal("21000.00"));
            repository.save(c2);
            mockMvc.perform(get("/api/creditos/7891011"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].numeroCredito").value("123456"))
                    .andExpect(jsonPath("$[1].numeroCredito").value("789012"));
        }

        @Test
        @DisplayName("Deve retornar 400 para parâmetro inválido (vazio)")
        void deveRetornar400ParametroVazio() throws Exception {
            mockMvc.perform(get("/api/creditos/ "))
                    .andExpect(status().is4xxClientError());
        }

        @Test
        @DisplayName("Deve retornar Content-Type application/json nos endpoints")
        void deveRetornarContentTypeJson() throws Exception {
            mockMvc.perform(get("/api/creditos/7891011"))
                    .andExpect(header().string("Content-Type", org.hamcrest.Matchers.containsString("application/json")));
        }

        @Test
        @DisplayName("Deve retornar 406 para Accept não suportado")
        void deveRetornar406ParaAcceptNaoSuportado() throws Exception {
            mockMvc.perform(get("/api/creditos/7891011").accept(MediaType.APPLICATION_XML))
                    .andExpect(status().isNotAcceptable());
        }

        @Test
        @DisplayName("Deve retornar 500 se ocorrer erro inesperado no service")
        void deveRetornar500ErroInterno() throws Exception {
            CreditoService mockService = mock(CreditoService.class);
            when(mockService.buscarPorNumeroNfse(anyString())).thenThrow(new RuntimeException("Erro simulado"));
            CreditoController controller = new CreditoController(mockService);
            @ControllerAdvice
            class SimpleAdvice {
                @ExceptionHandler(Exception.class)
                public org.springframework.http.ResponseEntity<String> handle(Exception ex) {
                    return org.springframework.http.ResponseEntity.status(500).body("Erro interno: " + ex.getMessage());
                }
            }
            MockMvc localMockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new SimpleAdvice())
                .build();
            localMockMvc.perform(get("/api/creditos/erro"))
                    .andExpect(status().isInternalServerError());
        }
    }

    @Nested
    @DisplayName("GET /api/creditos/credito/{numeroCredito}")
    class GetPorNumeroCredito {
        @Test
        @DisplayName("Deve retornar 200 e crédito para número de crédito existente")
        void deveRetornarCreditoExistente() throws Exception {
            mockMvc.perform(get("/api/creditos/credito/123456"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.numeroNfse").value("7891011"));
        }

        @Test
        @DisplayName("Deve retornar 404 para crédito inexistente")
        void deveRetornar404CreditoInexistente() throws Exception {
            mockMvc.perform(get("/api/creditos/credito/naoexiste"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Validação de parâmetros e payload")
    class ValidacaoPayload {
        @Test
        @DisplayName("Deve validar parâmetros obrigatórios e retornar erro adequado")
        void deveValidarParametrosObrigatorios() throws Exception {
            mockMvc.perform(get("/api/creditos/"))
                    .andExpect(status().is4xxClientError());
        }

        @Test
        @DisplayName("Deve retornar payload conforme especificação")
        void deveRetornarPayloadCorreto() throws Exception {
            mockMvc.perform(get("/api/creditos/7891011"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].numeroCredito").value("123456"))
                    .andExpect(jsonPath("$[0].valorIssqn").value(1500.75));
        }
    }
}
