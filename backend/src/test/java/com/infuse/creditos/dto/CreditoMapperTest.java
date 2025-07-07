package com.infuse.creditos.dto;

import com.infuse.creditos.domain.Credito;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de mapeamento de Credito para CreditoDTO")
class CreditoMapperTest {

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
    @DisplayName("Mapeamento de campos")
    class MapeamentoCampos {
        @Test
        @DisplayName("Deve mapear todos os campos corretamente de Credito para CreditoDTO")
        void deveMapearTodosOsCampos() {
            Credito credito = novoCredito("123456", "7891011");
            CreditoDTO dto = CreditoMapper.toDTO(credito);
            assertEquals("123456", dto.getNumeroCredito());
            assertEquals("7891011", dto.getNumeroNfse());
            assertEquals(LocalDate.of(2024, 2, 25), dto.getDataConstituicao());
            assertEquals(new BigDecimal("1500.75"), dto.getValorIssqn());
            assertEquals("ISSQN", dto.getTipoCredito());
            assertEquals("Sim", dto.getSimplesNacional());
            assertEquals(new BigDecimal("5.0"), dto.getAliquota());
            assertEquals(new BigDecimal("30000.00"), dto.getValorFaturado());
            assertEquals(new BigDecimal("5000.00"), dto.getValorDeducao());
            assertEquals(new BigDecimal("25000.00"), dto.getBaseCalculo());
        }
    }

    @Nested
    @DisplayName("Tratamento de campos nulos/ausentes")
    class CamposNulos {
        @Test
        @DisplayName("Deve tratar campos nulos/ausentes no mapeamento")
        void deveTratarCamposNulos() {
            CreditoDTO dto = CreditoMapper.toDTO(null);
            assertNull(dto);
        }
    }

    @Nested
    @DisplayName("Mapeamento de listas")
    class MapeamentoListas {
        @Test
        @DisplayName("Deve garantir que listas são mapeadas corretamente")
        void deveMapearListasCorretamente() {
            List<Credito> lista = Arrays.asList(novoCredito("1", "NFS1"), novoCredito("2", "NFS2"));
            List<CreditoDTO> dtos = CreditoMapper.toDTOList(lista);
            assertEquals(2, dtos.size());
            assertEquals("1", dtos.get(0).getNumeroCredito());
            assertEquals("NFS2", dtos.get(1).getNumeroNfse());
        }
    }

    @Nested
    @DisplayName("Mapeamento de listas vazias/nulas")
    class ListasVaziasNulas {
        @Test
        @DisplayName("Deve retornar lista vazia ao mapear lista vazia")
        void deveMapearListaVazia() {
            List<CreditoDTO> dtos = CreditoMapper.toDTOList(Collections.emptyList());
            assertNotNull(dtos);
            assertTrue(dtos.isEmpty());
        }
        @Test
        @DisplayName("Deve retornar lista vazia ao mapear lista nula")
        void deveMapearListaNula() {
            List<CreditoDTO> dtos = CreditoMapper.toDTOList(null);
            assertNotNull(dtos);
            assertTrue(dtos.isEmpty());
        }
    }

    @Nested
    @DisplayName("Equals, HashCode e ToString DTO")
    class EqualsHashCodeToStringDTO {
        @Test
        @DisplayName("Deve comparar objetos CreditoDTO corretamente (referência e campos)")
        void deveCompararObjetosDTO() {
            CreditoDTO d1 = new CreditoDTO();
            d1.setNumeroCredito("A");
            CreditoDTO d2 = new CreditoDTO();
            d2.setNumeroCredito("A");
            assertNotEquals(d1, d2); // equals padrão (referência)
            assertEquals(d1, d1);    // mesmo objeto
            assertNotEquals(d1, null);
            assertNotEquals(d1, new Object());
        }
        @Test
        @DisplayName("Deve gerar toString não nulo para DTO")
        void deveGerarToStringDTO() {
            CreditoDTO d = new CreditoDTO();
            assertNotNull(d.toString());
        }
        @Test
        @DisplayName("Deve gerar hashCode para objetos DTO diferentes")
        void deveGerarHashCodeDTO() {
            CreditoDTO d1 = new CreditoDTO();
            CreditoDTO d2 = new CreditoDTO();
            assertNotEquals(d1.hashCode(), d2.hashCode());
        }
    }
}
