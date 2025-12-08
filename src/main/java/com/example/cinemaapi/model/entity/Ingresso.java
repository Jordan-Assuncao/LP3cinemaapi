package com.example.cinemaapi.model.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean tipoIngresso;

    @ManyToOne
    private Sessao sessao;

    @OneToOne
    private Assento assento;

    @ManyToOne
    private Compra compra;

    public boolean isMeiaEntrada() {
        return tipoIngresso;
    }

    public boolean isInteira() {
        return !tipoIngresso;
    }

    public boolean hasAssento() {
        return assento != null && assento.getId() != null;
    }

    public boolean pertenceASessao(Long sessaoId) {
        return sessao != null && sessao.getId() != null && sessao.getId().equals(sessaoId);
    }

    public boolean isComprado() {
        return compra != null && compra.getId() != null;
    }

    public boolean assentoDisponivel() {
        return assento != null && assento.isDisponivel();
    }

    public boolean podeCancelar() {
        return compra != null && sessao != null && sessao.isDisponivelParaVenda();
    }

}
