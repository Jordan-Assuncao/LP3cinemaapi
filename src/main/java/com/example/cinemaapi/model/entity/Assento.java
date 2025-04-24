package com.example.cinemaapi.model.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroAssento;
    private int fileiraVertical;
    private int fileiraHorizontal;
    private boolean statusAssento;

    @ManyToOne
    private Sala sala;

    @ManyToOne
    private TipoAssento tipoAssento;
}
