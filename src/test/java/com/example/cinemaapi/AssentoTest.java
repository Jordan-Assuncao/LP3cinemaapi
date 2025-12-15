package com.example.cinemaapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.cinemaapi.model.entity.Sala;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.cinemaapi.model.entity.Assento;
import com.example.cinemaapi.model.entity.TipoAssento;

public class AssentoTest {
    @Test
    void deveRetornarDisponivelQuandoStatusForTrue() {
        Assento a = new Assento();
        a.setStatusAssento(true);

        assertTrue(a.isDisponivel());
    }

    @Test
    void deveRetornarBloqueadoQuandoStatusForTrue() {
        Assento a = new Assento();
        a.setStatusAssento(true);
        assertTrue(a.isBloqueado());
    }

    private TipoAssento criarTipoAssento(String nome) {
        return new TipoAssento(1L, nome, "Descrição base");
    }

    private Assento assentoBase;
    private TipoAssento tipoComum;
    private TipoAssento tipoPreferencial;

    @BeforeEach
    void setup() {

        tipoComum = criarTipoAssento("Normal");
        tipoPreferencial = criarTipoAssento("assento preferencial vip");


        assentoBase = new Assento();
        assentoBase.setNumeroAssento("A1");
        assentoBase.setStatusAssento(false);
        assentoBase.setTipoAssento(tipoComum);
        assentoBase.setSala(new Sala());
    }

    //TESTE 1
    @Test
    void deveRetornarOKQuandoTudoValido() {

        String res = assentoBase.avaliarCondicoesDeAssento(
                assentoBase,
                false,
                true,
                30
        );

        assertEquals("OK", res, "O assento válido e comum deve retornar OK.");
    }

    //TESTE 2
    @Test
    void deveRetornarErroAssentoInexistente() {
        String res = assentoBase.avaliarCondicoesDeAssento(
                null,
                true, true, 30
        );
        assertEquals("Assento inexistente", res, "P1 falhou.");
    }

    //TESTE 3
    @Test
    void deveRetornarErroNumeroAssentoNulo() {
        assentoBase.setNumeroAssento(null);
        String res = assentoBase.avaliarCondicoesDeAssento(
                assentoBase,
                true, true, 30
        );
        assertEquals("Assento inválido", res, "P2 falhou.");
    }
    //TESTE 4
    @Test
    void deveRetornarErroNumeroAssentoVazio() {
        assentoBase.setNumeroAssento("  ");
        String res = assentoBase.avaliarCondicoesDeAssento(
                assentoBase,
                true, true, 30
        );
        assertEquals("Assento inválido", res, "P3 falhou.");
    }
    //TESTE 5
    @Test
    void deveRetornarErroAssentoBloqueadoNaoPermitido() {
        assentoBase.setStatusAssento(true);

        String res = assentoBase.avaliarCondicoesDeAssento(
                assentoBase,
                true,
                false,
                30
        );
        assertEquals("Assento bloqueado", res, "P5 falhou.");
    }
    //TESTE 6
    @Test
    void deveRetornarErroPreferencialNaoPermitido() {
        assentoBase.setTipoAssento(tipoPreferencial);

        String res = assentoBase.avaliarCondicoesDeAssento(
                assentoBase,
                false,
                true,
                30
        );
        assertEquals("Assento preferencial não permitido", res, "P8 falhou.");
    }

    //TESTE 7
    @Test
    void deveRetornarErroPreferencialMenorDeIdade() {
        assentoBase.setTipoAssento(tipoPreferencial);

        String res = assentoBase.avaliarCondicoesDeAssento(
                assentoBase,
                true,
                true,
                17
        );
        assertEquals("Assento preferencial permitido apenas para maiores de idade", res, "P9 falhou.");
    }

    //TESTE 8
    @Test
    void deveRetornarErroPreferencialMenorQueDezAnos() {
        assentoBase.setTipoAssento(tipoPreferencial);

        String res = assentoBase.avaliarCondicoesDeAssento(
                assentoBase,
                true,
                true,
                9
        );

        assertEquals("Assento preferencial permitido apenas para maiores de idade",
                res, "P10 falhou, mas o P9 foi retornado primeiro (conforme a lógica do método).");
    }

    //TESTE 9
    @Test
    void devePassarQuandoAssentoNaoBloqueado() {
        assentoBase.setStatusAssento(false);

        String res = assentoBase.avaliarCondicoesDeAssento(
                assentoBase,
                true,
                false,
                30
        );
        assertEquals("OK", res, "P4 Falso falhou.");
    }

    //TESTE 10
    @Test
    void devePassarEmPreferencialPermitido() {
        assentoBase.setTipoAssento(tipoPreferencial);

        String res = assentoBase.avaliarCondicoesDeAssento(
                assentoBase,
                true,
                true,
                30
        );
        assertEquals("OK", res, "P8 Falso falhou.");
    }


    //TESTE 11
    @Test
    void devePassarEmPreferencialIdadeMaiorQueDezoito() {
        assentoBase.setTipoAssento(tipoPreferencial);

        String res = assentoBase.avaliarCondicoesDeAssento(
                assentoBase,
                true,
                true,
                18
        );
        assertEquals("OK", res, "P9 Falso falhou.");
    }
}