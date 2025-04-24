package com.example.cinemaapi.model.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroSala;
    private int capacidade;
    private int capacidadePreferencial;
    private String formatoSala;
    private int numeroFileiraVertical;
    private int numeroFileiraHorizontal;

    @ManyToOne
    private Unidade unidade;
}
