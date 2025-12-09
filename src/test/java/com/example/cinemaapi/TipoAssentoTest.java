package com.example.cinemaapi;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.example.cinemaapi.model.entity.TipoAssento;

public class TipoAssentoTest {
 @Test
    void isPreferencialNomeNull() {
        TipoAssento tipo = new TipoAssento(null, null, null);
        assertFalse(tipo.isPreferencial());
    }

    @Test
    void isPreferencialNenhumTermo() {
        TipoAssento tipo = new TipoAssento(1L, "Normal", "desc");
        assertFalse(tipo.isPreferencial());
    }

    @Test
    void isPreferencialContemPreferencial() {
        TipoAssento tipo = new TipoAssento(1L, "assento preferencial vip", "desc");
        assertTrue(tipo.isPreferencial());
    }

    @Test
    void isPreferencialContemIdoso() {
        TipoAssento tipo = new TipoAssento(1L, "assento idoso", "desc");
        assertTrue(tipo.isPreferencial());
    }

    @Test
    void isPreferencialContemGestante() {
        TipoAssento tipo = new TipoAssento(1L, "gestante especial", "desc");
        assertTrue(tipo.isPreferencial());
    }


    @Test
    void isParaPcdNomeNull() {
        TipoAssento tipo = new TipoAssento(null, null, null);
        assertFalse(tipo.isParaPcd());
    }

    @Test
    void isParaPcdNenhumTermo() {
        TipoAssento tipo = new TipoAssento(1L, "Normal", "desc");
        assertFalse(tipo.isParaPcd());
    }

    @Test
    void isParaPcdContemPCD() {
        TipoAssento tipo = new TipoAssento(1L, "assento pcd lateral", "desc");
        assertTrue(tipo.isParaPcd());
    }

    @Test
    void isParaPcdContemAcessibilidade() {
        TipoAssento tipo = new TipoAssento(1L, "assento acessibilidade frontal", "desc");
        assertTrue(tipo.isParaPcd());
    }
}
