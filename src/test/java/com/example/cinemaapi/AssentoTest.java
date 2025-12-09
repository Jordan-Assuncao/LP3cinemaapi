package com.example.cinemaapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void deveRetornarAssentoInvalidoQuandoNumeroAssentoForNull() {
        Assento assento = new Assento();
        assento.setNumeroAssento(null);
        assento.setFileiraHorizontal(5);
        assento.setFileiraVertical(5);
        assento.setStatusAssento(false);
        assento.setTipoAssento(null);

        String resultado = assento.avaliarCondicoesDeAssento(
                assento,
                true,
                true,
                30);

        assertEquals("Assento inválido", resultado);
    }

    @Test
    void deveRetornarAssentoBloqueadoQuandoAssentoEstaBloqueadoEPermitirBloqueadoFalse() {
        Assento assento = new Assento();
        assento.setId(1L);
        assento.setNumeroAssento("A10");
        assento.setFileiraVertical(5);
        assento.setFileiraHorizontal(5);
        assento.setStatusAssento(true);
        assento.setTipoAssento(null);

        String resultado = assento.avaliarCondicoesDeAssento(
                assento,
                true,
                false,
                30);

        assertEquals("Assento bloqueado", resultado);
    }

    @Test
    void deveProsseguirQuandoAssentoBloqueadoMasPermitirBloqueadoTrue() {
        Assento assento = new Assento();
        assento.setId(2L);
        assento.setNumeroAssento("B10");
        assento.setFileiraVertical(5);
        assento.setFileiraHorizontal(5);
        assento.setStatusAssento(true);
        assento.setTipoAssento(null);

        String resultado = assento.avaliarCondicoesDeAssento(
                assento,
                true,
                true,
                30);
        assertEquals("OK", resultado);
    }

    @Test
    void deveRetornarAssentoInexistenteQuandoNulo() {
        Assento a = new Assento();
        String msg = a.avaliarCondicoesDeAssento(null, true, true, 30);
        assertEquals("Assento inexistente", msg);
    }

    @Test
    void deveRetornarAssentoInvalidoQuandoNumeroAssentoVazio() {
        Assento a = new Assento();
        Assento invalid = new Assento(null, "", 3, 3, true, null, null);

        String msg = a.avaliarCondicoesDeAssento(invalid, true, true, 25);

        assertEquals("Assento inválido", msg);
    }

    @Test
    void deveRetornarAssentoBloqueadoQuandoNaoPermiteBloqueado() {
        Assento a = new Assento();
        Assento bloqueado = new Assento(null, "A1", 3, 3, true, null, null);

        String msg = a.avaliarCondicoesDeAssento(bloqueado, true, false, 30);

        assertEquals("Assento bloqueado", msg);
    }

    @Test
    void deveRecusarAssentoPreferencialSemPermissao() {
        Assento a = new Assento();

        TipoAssento tipo = new TipoAssento();
        tipo.setNomeAssento("PREFERENCIAL");

        Assento preferencial = new Assento(null, "B1", 2, 5, false, null, tipo);

        String msg = a.avaliarCondicoesDeAssento(preferencial, false, true, 30);

        assertEquals("Assento preferencial não permitido", msg);
    }

    @Test
    void deveRecusarPreferencialParaMenorDeIdade() {
        Assento a = new Assento();

        TipoAssento tipo = new TipoAssento();
        tipo.setNomeAssento("PREFERENCIAL");

        Assento preferencial = new Assento(null, "C1", 2, 5, false, null, tipo);

        String msg = a.avaliarCondicoesDeAssento(preferencial, true, true, 17);

        assertEquals("Assento preferencial permitido apenas para maiores de idade", msg);
    }

    @Test
    void deveRecusarPreferencialParaAdultoNaoPcd() {
        Assento a = new Assento();

        TipoAssento tipo = new TipoAssento();
        tipo.setNomeAssento("PREFERENCIAL");

        Assento preferencial = new Assento(null, "D1", 3, 4, false, null, tipo);

        String msg = a.avaliarCondicoesDeAssento(preferencial, true, true, 59);

        assertEquals("Assento preferencial prioritário para idosos ou PCD", msg);
    }

    @Test
    void deveAceitarPreferencialParaPcd() {
        Assento a = new Assento();

        TipoAssento tipo = new TipoAssento();
        tipo.setNomeAssento("PCD");

        Assento preferencial = new Assento(null, "D1", 3, 4, false, null, tipo);

        String msg = a.avaliarCondicoesDeAssento(preferencial, true, true, 59);

        assertEquals("OK", msg);
    }

    @Test
    void deveDetectarPosicaoInvalida() {
        Assento a = new Assento();
        Assento invalido = new Assento(null, "E1", -1, -5, false, null, null);

        String msg = a.avaliarCondicoesDeAssento(invalido, true, true, 35);

        assertEquals("Posição inválida", msg);
    }

    // 1) preferencial NÃO permitido -> deve retornar "Assento preferencial não
    // permitido"
    @Test
    void preferencialNaoPermitido_retornaMensagem() {
        TipoAssento tipo = new TipoAssento();
        tipo.setNomeAssento("PREFERENCIAL");

        Assento assento = new Assento();
        assento.setNumeroAssento("P1");
        assento.setFileiraVertical(5);
        assento.setFileiraHorizontal(5);
        assento.setStatusAssento(false);
        assento.setTipoAssento(tipo);

        String resultado = assento.avaliarCondicoesDeAssento(
                assento,
                false,
                true,
                30);

        assertEquals("Assento preferencial não permitido", resultado);
    }

    // 2) preferencial, permitido, idade < 18 -> "Assento preferencial permitido
    // apenas para maiores de idade"
    @Test
    void preferencialMenorDeIdade_retornaMensagem() {
        TipoAssento tipo = new TipoAssento();
        tipo.setNomeAssento("PREFERENCIAL");

        Assento assento = new Assento();
        assento.setNumeroAssento("P2");
        assento.setFileiraVertical(3);
        assento.setFileiraHorizontal(4);
        assento.setStatusAssento(false);
        assento.setTipoAssento(tipo);

        String resultado = assento.avaliarCondicoesDeAssento(
                assento,
                true,
                true,
                17);

        assertEquals("Assento preferencial permitido apenas para maiores de idade", resultado);
    }

    // 3) preferencial, permitido, idade entre 18 e 59 e NÃO PCD ->
    // "Assento preferencial prioritário para idosos ou PCD"
    @Test
    void preferencialAdultoNaoPcd_retornaPrioritario() {
        TipoAssento tipo = new TipoAssento();
        tipo.setNomeAssento("PREFERENCIAL"); // garante isParaPcd() == false

        Assento assento = new Assento();
        assento.setNumeroAssento("P3");
        assento.setFileiraVertical(4);
        assento.setFileiraHorizontal(6);
        assento.setStatusAssento(false);
        assento.setTipoAssento(tipo);

        String resultado = assento.avaliarCondicoesDeAssento(
                assento,
                true, // permitirPreferencial
                true,
                59 // 18 <= idade < 60
        );

        assertEquals("Assento preferencial prioritário para idosos ou PCD", resultado);
    }

    // 4) preferencial, permitido, idade >= 60
    @Test
    void preferencialIdoso_naoRetornaNoBloco_eContinuaOK() {
        TipoAssento tipo = new TipoAssento();
        tipo.setNomeAssento("PREFERENCIAL");

        Assento assento = new Assento();
        assento.setNumeroAssento("P4");
        assento.setFileiraVertical(10);
        assento.setFileiraHorizontal(8);
        assento.setStatusAssento(false);
        assento.setTipoAssento(tipo);

        String resultado = assento.avaliarCondicoesDeAssento(
                assento,
                true,
                true,
                61);

        assertEquals("OK", resultado);
    }

    // 5) preferencial, permitido, adulto (<60) MAS tipo é PCD -> !isParaPcd() ==
    // false,
    // portanto não deve cair no retorno "prioritário" e continua (retorna "OK")
    @Test
    void preferencialAdultoMasTipoPcd_naoRetornaPrioritario() {
        TipoAssento tipo = new TipoAssento();
        tipo.setNomeAssento("PREFERENCIAL PCD");

        Assento assento = new Assento();
        assento.setNumeroAssento("P5");
        assento.setFileiraVertical(6);
        assento.setFileiraHorizontal(6);
        assento.setStatusAssento(false);
        assento.setTipoAssento(tipo);

        String resultado = assento.avaliarCondicoesDeAssento(
                assento,
                true,
                true,
                59 // idade < 60, mas tipo.isParaPcd() == true -> NÃO retorna a mensagem
                   // prioritaria
        );

        assertEquals("OK", resultado);
    }

    @Test
    void posicaoComFileiraVerticalNegativa_retornaInvalida() {
        Assento assento = new Assento();
        assento.setNumeroAssento("A1");
        assento.setStatusAssento(false);
        assento.setFileiraVertical(-1);
        assento.setFileiraHorizontal(5);

        String resultado = assento.avaliarCondicoesDeAssento(
                assento,
                true,
                true,
                30);

        assertEquals("Posição inválida", resultado);
    }

    @Test
    void posicaoComFileiraHorizontalNegativa_retornaInvalida() {
        Assento assento = new Assento();
        assento.setNumeroAssento("A2");
        assento.setStatusAssento(false);
        assento.setFileiraVertical(3);
        assento.setFileiraHorizontal(-2);

        String resultado = assento.avaliarCondicoesDeAssento(
                assento,
                true,
                true,
                30);

        assertEquals("Posição inválida", resultado);
    }

    @Test
    void posicaoValida_naoRetornaAqui() {
        Assento assento = new Assento();
        assento.setNumeroAssento("A3");
        assento.setStatusAssento(false);
        assento.setFileiraVertical(2);
        assento.setFileiraHorizontal(4);

        String resultado = assento.avaliarCondicoesDeAssento(
                assento,
                true,
                true,
                30);
        assertEquals("OK", resultado);
    }

    @Test
    void assentoDistanteParaIdoso_retornaMensagemCorreta() {
        Assento assento = new Assento();
        assento.setNumeroAssento("B10");
        assento.setStatusAssento(false);
        assento.setFileiraVertical(2);
        assento.setFileiraHorizontal(25); // > 20

        String resultado = assento.avaliarCondicoesDeAssento(
                assento,
                true,
                true,
                70 // idoso
        );

        assertEquals("Assento distante demais para idosos", resultado);
    }

    @Test
    void assentoDistanteParaCrianca_retornaMensagemCorreta() {
        Assento assento = new Assento();
        assento.setNumeroAssento("B11");
        assento.setStatusAssento(false);
        assento.setFileiraVertical(2);
        assento.setFileiraHorizontal(25); 

        String resultado = assento.avaliarCondicoesDeAssento(
                assento,
                true,
                true,
                8 // criança
        );

        assertEquals("Assento não recomendado para crianças", resultado);
    }

    @Test
    void assentoDistanteComIdadeNormal_naoRetornaMensagens() {
        Assento assento = new Assento();
        assento.setNumeroAssento("B12");
        assento.setStatusAssento(false);
        assento.setFileiraVertical(2);
        assento.setFileiraHorizontal(25);

        String resultado = assento.avaliarCondicoesDeAssento(
                assento,
                true,
                true,
                30
        );
        assertEquals("OK", resultado);
    }

    @Test
    void deveRejeitarAssentoMuitoProximoParaCrianca() {
        Assento a = new Assento();
        Assento s = new Assento(null, "G1", 1, 10, false, null, null);

        String msg = a.avaliarCondicoesDeAssento(s, true, true, 10);

        assertEquals("Assento muito próximo da tela para crianças", msg);
    }

    @Test
    void deveRejeitarAssentoMuitoProximoParaIdoso() {
        Assento a = new Assento();
        Assento s = new Assento(null, "G2", 2, 12, false, null, null);

        String msg = a.avaliarCondicoesDeAssento(s, true, true, 75);

        assertEquals("Assento muito próximo da tela para idosos", msg);
    }

    @Test
    void deveRetornarOkQuandoTodasAsRegrasForemAtendidas() {
        Assento a = new Assento();
        Assento valido = new Assento(null, "H1", 5, 10, false, null, null);

        String msg = a.avaliarCondicoesDeAssento(valido, true, true, 40);

        assertEquals("OK", msg);
    }

}
