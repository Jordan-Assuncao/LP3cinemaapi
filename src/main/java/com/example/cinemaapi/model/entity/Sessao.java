package com.example.cinemaapi.model.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataSessao;
    private String horaSessao;
    private boolean statusSessao;
    private boolean dublado;
    private boolean legendado;

    @ManyToOne
    private Sala sala;

    @ManyToOne
    private Filme filme;

    @ManyToOne
    private Preco preco;
}
