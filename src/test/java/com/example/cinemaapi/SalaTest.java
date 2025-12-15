    package com.example.cinemaapi;
    
    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertFalse;
    import static org.junit.jupiter.api.Assertions.assertTrue;
    
    import org.junit.jupiter.api.Test;
    
    import com.example.cinemaapi.model.entity.Sala;
    import com.example.cinemaapi.model.entity.Unidade;
    
    public class SalaTest {
        private Sala criarBase() {
            Sala sala = new Sala();
            sala.setNumeroSala("1");
            sala.setCapacidade(50);
            sala.setCapacidadePreferencial(5);
            sala.setNumeroFileiraVertical(5);
            sala.setNumeroFileiraHorizontal(10);
            sala.setFormatoSala("retangular");
    
            Unidade u = new Unidade();
            u.setId(1L);
            sala.setUnidade(u);
    
            return sala;
        }
        private Sala criarNovaBaseValida() {
            Sala sala = new Sala();
            sala.setCapacidade(50);
            sala.setCapacidadePreferencial(5);
            sala.setNumeroFileiraVertical(10);
            sala.setNumeroFileiraHorizontal(10);
            sala.setFormatoSala("retangular");
            sala.setUnidade(new Unidade());
            return sala;
        }
    
        @Test
        void capacidadeTotalSomenteComum() {
            Sala sala = criarBase();
            sala.setCapacidadePreferencial(0);
    
            assertEquals(sala.getCapacidade(), sala.getCapacidadeTotal());
        }
    
        @Test
        void capacidadeTotalComPreferenciais() {
            Sala sala = criarBase();
            sala.setCapacidade(30);
            sala.setCapacidadePreferencial(10);
    
            assertEquals(40, sala.getCapacidadeTotal());
        }
    
        @Test
        void possuiPreferenciaisFalse() {
            Sala sala = criarBase();
            sala.setCapacidadePreferencial(0);
    
            assertFalse(sala.possuiAssentosPreferenciais());
        }
    
        @Test
        void possuiPreferenciaisTrue() {
            Sala sala = criarBase();
            sala.setCapacidadePreferencial(5);
    
            assertTrue(sala.possuiAssentosPreferenciais());
        }
    
        @Test
        void capacidadeCompativelTrue() {
            Sala sala = criarBase();
            sala.setCapacidade(20);
            sala.setCapacidadePreferencial(5);
            sala.setNumeroFileiraVertical(5);
            sala.setNumeroFileiraHorizontal(6); // grid = 30
    
            assertTrue(sala.isCapacidadeCompatível());
        }
    
        @Test
        void capacidadeCompativelFalse() {
            Sala sala = criarBase();
            sala.setCapacidade(40);
            sala.setCapacidadePreferencial(20); // total = 60
            sala.setNumeroFileiraVertical(5);
            sala.setNumeroFileiraHorizontal(10); // grid = 50
    
            assertFalse(sala.isCapacidadeCompatível());
        }
    
        @Test
        void percentualPreferenciaisCapacidadeTotalZero() {
            Sala sala = criarBase();
            sala.setCapacidade(0);
            sala.setCapacidadePreferencial(0);
    
            assertEquals(0, sala.getPercentualPreferenciais());
        }
    
        @Test
        void percentualPreferenciaisCalculado() {
            Sala sala = criarBase();
            sala.setCapacidade(40);
            sala.setCapacidadePreferencial(10);
    
            assertEquals(0.2, sala.getPercentualPreferenciais(), 0.0001);
        }


        //Testes do método de complexidade 11
        // 1° Teste – Caminho feliz
        @Test
        void deveRetornarSucessoConfiguracaoValida() {
            Sala sala = criarNovaBaseValida();
            String res = sala.validarPoliticasComerciais();
            assertEquals("Políticas comerciais validadas com sucesso.", res);
        }
        // 2° Teste – Capacidade mínima não atingida
        @Test
        void deveRetornarErroCapacidadeMinima() {
            Sala sala = criarNovaBaseValida();
            sala.setCapacidade(9);
            String res = sala.validarPoliticasComerciais();
            assertTrue(res.contains("Capacidade mínima não atingida."));
        }
        // 3° Teste – Capacidade máxima excedida
        @Test
        void deveRetornarErroCapacidadeMaxima() {
            Sala sala = criarNovaBaseValida();
            sala.setCapacidade(151);
            String res = sala.validarPoliticasComerciais();
            assertTrue(res.contains("Capacidade máxima excedida."));
        }
        // 4° Teste – Assentos preferenciais em sala pequena
        @Test
        void deveRetornarErroPreferenciaisSalaPequena() {
            Sala sala = criarNovaBaseValida();
            sala.setCapacidade(15);
            sala.setCapacidadePreferencial(2);

            String res = sala.validarPoliticasComerciais();

            assertTrue(res.contains("Sala pequena demais para assentos preferenciais."));
        }
        // 5° Teste – Densidade muito baixa
        @Test
        void deveRetornarErroDensidadeBaixa() {
            Sala sala = criarNovaBaseValida();
            sala.setCapacidade(10);
            sala.setCapacidadePreferencial(0);

            String res = sala.validarPoliticasComerciais();
            assertTrue(res.contains("Densidade de assentos muito baixa."));
        }
        // 6° Teste – Densidade muito alta
        @Test
        void deveRetornarErroDensidadeAlta() {
            Sala sala = criarNovaBaseValida();
            sala.setCapacidade(99);
            sala.setCapacidadePreferencial(1);
            String res = sala.validarPoliticasComerciais();
            assertTrue(res.contains("Densidade de assentos muito alta."));
        }

        // 7° Teste – Número insuficiente de fileiras verticais
        @Test
        void deveRetornarErroFileirasVerticaisInsuficientes() {
            Sala sala = criarNovaBaseValida();
            sala.setNumeroFileiraVertical(2);

            String res = sala.validarPoliticasComerciais();
            assertTrue(res.contains("Número insuficiente de fileiras."));
        }
        // 8° Teste – Número insuficiente de fileiras horizontais
        @Test
        void deveRetornarErroFileirasHorizontaisInsuficientes() {
            Sala sala = criarNovaBaseValida();
            sala.setNumeroFileiraHorizontal(2);

            String res = sala.validarPoliticasComerciais();

            assertTrue(res.contains("Número insuficiente de fileiras."));
        }
        // 9° Teste – Sala sem unidade vinculada
        @Test
        void deveRetornarErroSalaSemUnidade() {
            Sala sala = criarNovaBaseValida();
            sala.setUnidade(null);

            String res = sala.validarPoliticasComerciais();

            assertTrue(res.contains("Sala sem unidade vinculada."));
        }
        // 10° Teste – Caminho alternativo válido (densidade ok)
        @Test
        void naoDeveEntrarEmErroDeDensidade() {
            Sala sala = criarNovaBaseValida();
            sala.setCapacidade(40);

            String res = sala.validarPoliticasComerciais();

            assertEquals("Políticas comerciais validadas com sucesso.", res);
        }

        // 11° Teste – Retorno com múltiplos erros acumulados
        @Test
        void deveRetornarListaDeErros() {
            Sala sala = criarNovaBaseValida();
            sala.setCapacidade(9);
            sala.setUnidade(null);

            String res = sala.validarPoliticasComerciais();

            assertTrue(res.contains("Capacidade mínima"));
            assertTrue(res.contains("Sala sem unidade"));
        }
    }
