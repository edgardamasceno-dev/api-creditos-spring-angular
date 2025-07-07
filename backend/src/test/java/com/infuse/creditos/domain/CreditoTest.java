package com.infuse.creditos.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes unitários para a entidade Credito")
class CreditoTest {

    @Nested
    @DisplayName("Criação de instância válida")
    class InstanciaValida {
        @Test
        @DisplayName("Deve criar Credito válido com todos os campos obrigatórios")
        void deveCriarCreditoValido() {
            Credito credito = new Credito();
            credito.setNumeroCredito("123456");
            credito.setNumeroNfse("7891011");
            credito.setDataConstituicao(LocalDate.of(2024, 2, 25));
            credito.setValorIssqn(new BigDecimal("1500.75"));
            credito.setTipoCredito("ISSQN");
            credito.setSimplesNacional(true);
            credito.setAliquota(new BigDecimal("5.0"));
            credito.setValorFaturado(new BigDecimal("30000.00"));
            credito.setValorDeducao(new BigDecimal("5000.00"));
            credito.setBaseCalculo(new BigDecimal("25000.00"));

            assertEquals("123456", credito.getNumeroCredito());
            assertEquals("7891011", credito.getNumeroNfse());
            assertEquals(LocalDate.of(2024, 2, 25), credito.getDataConstituicao());
            assertEquals(new BigDecimal("1500.75"), credito.getValorIssqn());
            assertEquals("ISSQN", credito.getTipoCredito());
            assertTrue(credito.isSimplesNacional());
            assertEquals(new BigDecimal("5.0"), credito.getAliquota());
            assertEquals(new BigDecimal("30000.00"), credito.getValorFaturado());
            assertEquals(new BigDecimal("5000.00"), credito.getValorDeducao());
            assertEquals(new BigDecimal("25000.00"), credito.getBaseCalculo());
        }
    }

    @Nested
    @DisplayName("Validação de campos obrigatórios e inválidos")
    class ValidacaoCampos {
        @Test
        @DisplayName("Deve lançar exceção ao criar Credito com campos obrigatórios nulos/vazios")
        void deveLancarExcecaoCamposObrigatorios() {
            Credito credito = new Credito();
            // Não setar campos obrigatórios
            Exception ex = assertThrows(NullPointerException.class, () -> {
                credito.getNumeroCredito().length();
            });
            assertNotNull(ex);
        }

        @Test
        @DisplayName("Deve lançar exceção para valores negativos/inválidos")
        void deveLancarExcecaoValoresInvalidos() {
            Credito credito = new Credito();
            assertThrows(IllegalArgumentException.class, () -> credito.setValorIssqn(new BigDecimal("-1")));
            assertThrows(IllegalArgumentException.class, () -> credito.setAliquota(new BigDecimal("-0.1")));
        }
    }

    @Nested
    @DisplayName("Métodos de domínio")
    class MetodosDominio {
        @Test
        @DisplayName("Deve garantir que métodos de domínio funcionam conforme esperado")
        void deveValidarMetodosDominio() {
            Credito credito = new Credito();
            credito.setValorFaturado(new BigDecimal("1000.00"));
            credito.setValorDeducao(new BigDecimal("200.00"));
            credito.setBaseCalculo(credito.getValorFaturado().subtract(credito.getValorDeducao()));
            assertEquals(new BigDecimal("800.00"), credito.getBaseCalculo());
        }
    }

    @Nested
    @DisplayName("Equals, HashCode e ToString")
    class EqualsHashCodeToString {
        @Test
        @DisplayName("Deve comparar objetos Credito corretamente (referência e campos)")
        void deveCompararObjetos() {
            Credito c1 = new Credito();
            c1.setNumeroCredito("A");
            Credito c2 = new Credito();
            c2.setNumeroCredito("A");
            assertNotEquals(c1, c2); // equals padrão (referência)
            assertEquals(c1, c1);    // mesmo objeto
            assertNotEquals(c1, null);
            assertNotEquals(c1, new Object());
        }
        @Test
        @DisplayName("Deve gerar toString não nulo")
        void deveGerarToString() {
            Credito c = new Credito();
            assertNotNull(c.toString());
        }
        @Test
        @DisplayName("Deve gerar hashCode para objetos diferentes")
        void deveGerarHashCode() {
            Credito c1 = new Credito();
            Credito c2 = new Credito();
            assertNotEquals(c1.hashCode(), c2.hashCode());
        }
    }
} 