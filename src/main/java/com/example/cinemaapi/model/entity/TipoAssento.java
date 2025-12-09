package com.example.cinemaapi.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoAssento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeAssento; // exemplo: NORMAL, PREFERENCIAL, PCD, PREMIUM
    private String descricao;


    public boolean isPreferencial() {
        if (nomeAssento == null)
            return false;

        String n = nomeAssento.toUpperCase();

        return n.contains("PREFERENCIAL") || n.contains("IDOSO") || n.contains("GESTANTE");
    }

    public boolean isParaPcd() {
        if (nomeAssento == null)
            return false;

        String n = nomeAssento.toUpperCase();
        return n.contains("PCD") || n.contains("ACESSIBILIDADE");
    }



}


