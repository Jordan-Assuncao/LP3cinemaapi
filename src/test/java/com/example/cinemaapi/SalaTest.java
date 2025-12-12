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

    @Test // 1
    void numeroSalaNulo() {
        Sala sala = criarBase();
        sala.setNumeroSala(null);

        String res = sala.validarConfiguracaoSala();
        assertTrue(res.contains("Número da sala inválido."));
    }

    @Test // 2
    void numeroSalaVazia() {
        Sala sala = criarBase();
        sala.setNumeroSala("");

        String res = sala.validarConfiguracaoSala();
        assertTrue(res.contains("Número da sala inválido."));
    }

    @Test
    void numeroSalaInvalido() {
        Sala sala = criarBase();
        sala.setNumeroSala(" ");

        String res = sala.validarConfiguracaoSala();
        assertTrue(res.contains("Número da sala inválido."));
    }

    @Test // 3
    void capacidadeInvalida() {
        Sala sala = criarBase();
        sala.setCapacidade(0);

        String res = sala.validarConfiguracaoSala();
        assertTrue(res.contains("A capacidade da sala deve ser maior que zero."));
    }

    @Test // 4
    void capacidadePreferencialNegativa() {
        Sala sala = criarBase();
        sala.setCapacidadePreferencial(-1);

        String res = sala.validarConfiguracaoSala();
        assertTrue(res.contains("A capacidade preferencial não pode ser negativa."));
    }

    @Test // 5
    void preferenciaisExcedemComuns() {
        Sala sala = criarBase();
        sala.setCapacidadePreferencial(100);

        String res = sala.validarConfiguracaoSala();
        assertTrue(res.contains("Assentos preferenciais não podem exceder os assentos comuns."));
    }

    // fileiras inválidas
    @Test // 6
    void fileirasInvalidas() {
        Sala sala = criarBase();
        sala.setNumeroFileiraVertical(0);

        String res = sala.validarConfiguracaoSala();
        assertTrue(res.contains("Quantidade de fileiras inválida."));
    }

    @Test // 7
    void fileirasHorizontaisInvalidas() {
        Sala sala = criarBase();
        sala.setNumeroFileiraHorizontal(0);

        String res = sala.validarConfiguracaoSala();
        assertTrue(res.contains("Quantidade de fileiras inválida."));
    }

    @Test //8
    void gridInsuficiente() {
        Sala sala = criarBase();
        sala.setCapacidade(100);
        sala.setNumeroFileiraVertical(5);
        sala.setNumeroFileiraHorizontal(10); // grid = 50

        String res = sala.validarConfiguracaoSala();
        assertTrue(res.contains("Capacidade total excede o número de posições possíveis no grid."));
    }

    @Test //9
    void formatoSalaNulo() {
        Sala sala = criarBase();
        sala.setFormatoSala(null);

        String res = sala.validarConfiguracaoSala();
        assertTrue(res.contains("Formato da sala inválido."));
    }

    @Test //10
    void unidadeInexistente() {
        Sala sala = criarBase();
        sala.setUnidade(null);

        String res = sala.validarConfiguracaoSala();
        assertTrue(res.contains("Sala não vinculada a nenhuma unidade."));
    }

    @Test //11
    void imaxComCapacidadeInsuficiente() {
        Sala sala = criarBase();
        sala.setFormatoSala("imax");
        sala.setCapacidade(29);

        String res = sala.validarConfiguracaoSala();
        assertTrue(res.contains("Uma sala IMAX não pode ter capacidade inferior a 30 lugares."));
    }

    @Test //12
    void salaPequenaComPreferenciais() {
        Sala sala = criarBase();
        sala.setCapacidade(15);
        sala.setCapacidadePreferencial(3);

        String res = sala.validarConfiguracaoSala();
        assertTrue(res.contains("Sala pequena demais para possuir assentos preferenciais."));
    }

    @Test //13
    void densidadeMuitoBaixa() {
        Sala sala = new Sala();
        sala.setNumeroSala("1");
        sala.setCapacidade(10); // densidade = 10/50 = 0.2
        sala.setCapacidadePreferencial(0);
        sala.setNumeroFileiraVertical(5);
        sala.setNumeroFileiraHorizontal(10);
        sala.setFormatoSala("retangular");

        Unidade u = new Unidade();
        u.setId(1L);
        sala.setUnidade(u);

        String res = sala.validarConfiguracaoSala();

        assertTrue(res.contains("Densidade de assentos muito baixa para uma sala comercial."));
    }

    // densidade > 0.95
    @Test //14
    void densidadeMuitoAlta() {
        Sala sala = criarBase();
        sala.setCapacidade(48); // grid = 50 → 0.96

        String res = sala.validarConfiguracaoSala();
        assertTrue(res.contains("Densidade de assentos muito alta e pode prejudicar mobilidade."));
    }

    @Test
    void variosErrosAoMesmoTempo() {
        Sala sala = new Sala();
        sala.setNumeroSala("");
        sala.setCapacidade(0);
        sala.setCapacidadePreferencial(10);
        sala.setNumeroFileiraVertical(0);
        sala.setNumeroFileiraHorizontal(0);
        sala.setFormatoSala("");
        sala.setUnidade(null);

        String res = sala.validarConfiguracaoSala();

        assertTrue(res.contains("Número da sala inválido."));
        assertTrue(res.contains("A capacidade da sala deve ser maior que zero."));
        assertTrue(res.contains("Assentos preferenciais não podem exceder os assentos comuns."));
        assertTrue(res.contains("Quantidade de fileiras inválida."));
        assertTrue(res.contains("Formato da sala inválido."));
        assertTrue(res.contains("Sala não vinculada a nenhuma unidade."));
    }

    @Test
    void fileirasInvalidasVerticalZero() {
        Sala sala = criarBase();
        sala.setNumeroFileiraVertical(0);

        String res = sala.validarConfiguracaoSala();
        assertTrue(res.contains("Quantidade de fileiras inválida."));
    }

    @Test
    void fileirasInvalidasHorizontalZero() {
        Sala sala = criarBase();
        sala.setNumeroFileiraHorizontal(0);

        String res = sala.validarConfiguracaoSala();
        assertTrue(res.contains("Quantidade de fileiras inválida."));
    }

    @Test
    void formatoOvalValido() {
        Sala sala = criarBase();
        sala.setFormatoSala("oval");

        String res = sala.validarConfiguracaoSala();
        assertFalse(res.contains("Formato da sala não reconhecido."));
        assertFalse(res.contains("Formato da sala inválido."));
    }

    @Test
    void formatoPremiumValido() {
        Sala sala = criarBase();
        sala.setFormatoSala("premium");

        String res = sala.validarConfiguracaoSala();
        assertFalse(res.contains("Formato da sala não reconhecido."));
        assertFalse(res.contains("Formato da sala inválido."));
    }

    @Test //15
    void configuracaoValida() {
        Sala sala = new Sala();
        sala.setNumeroSala("1");
        sala.setCapacidade(20);
        sala.setCapacidadePreferencial(10);
        sala.setNumeroFileiraVertical(4);
        sala.setNumeroFileiraHorizontal(10);
        sala.setFormatoSala("retangular");

        Unidade unidade = new Unidade();
        unidade.setId(1L);
        sala.setUnidade(unidade);

        String res = sala.validarConfiguracaoSala();

        assertEquals("Configuração da sala válida.", res);
    }

}
