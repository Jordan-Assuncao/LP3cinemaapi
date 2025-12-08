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

    /**
     * Retorna true se o tipo do assento for considerado preferencial.
     */
    public boolean isPreferencial() {
        if (nomeAssento == null)
            return false;

        String n = nomeAssento.toUpperCase();

        return n.contains("PREFERENCIAL") || n.contains("IDOSO") || n.contains("GESTANTE");
    }

    /**
     * Retorna true se o assento é destinado a PCD.
     */
    public boolean isParaPcd() {
        if (nomeAssento == null)
            return false;

        String n = nomeAssento.toUpperCase();
        return n.contains("PCD") || n.contains("ACESSIBILIDADE");
    }

    /**
     * Método propositalmente complexo para cálculos de métricas.
     * Complexidade: 16.
     */
    public String avaliarTipoAssento(int idade, boolean possuiDeficiencia, boolean ehGestante) {

        if (nomeAssento == null || nomeAssento.isBlank()) {
            return "Tipo inválido";
        }

        String tipo = nomeAssento.toUpperCase();

        // Regras para assentos PCD
        if (tipo.contains("PCD")) {
            if (possuiDeficiencia) {
                return "Permitido para PCD";
            } else if (idade > 60) {
                return "Permitido para idosos em assento PCD";
            } else {
                return "Assento PCD restrito";
            }
        }

        // Preferencial
        if (tipo.contains("PREFERENCIAL")) {
            if (idade >= 60) {
                return "Preferencial para idosos";
            } else if (ehGestante) {
                return "Preferencial para gestante";
            } else if (possuiDeficiencia) {
                return "Preferencial para PCD";
            } else {
                return "Assento preferencial, porém sem prioridade";
            }
        }

        // Assentos Premium
        if (tipo.contains("PREMIUM") || tipo.contains("LUXO")) {
            if (idade < 12) {
                return "Crianças não podem ocupar assentos premium";
            }
            if (idade > 75) {
                return "Idosos podem ocupar, porém com restrição";
            }
            return "Acesso liberado ao assento premium";
        }

        // Assentos duplos
        if (tipo.contains("DUPLO") || tipo.contains("NAMORADEIRA")) {
            if (idade < 14) {
                return "Assento duplo proibido para menores de 14 anos";
            }
            if (ehGestante) {
                return "Gestantes não devem utilizar assentos duplos";
            }
            return "Assento duplo permitido";
        }

        // Assentos normais
        if (tipo.contains("NORMAL") || tipo.contains("PADRAO")) {
            if (idade < 3) {
                return "Assento não recomendado para bebês";
            }
            return "Assento padrão liberado";
        }

        // Caso nenhum tipo seja identificado
        return "Tipo de assento não reconhecido";
    }


}


