package com.example.cinemaapi.model.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String sinopse;
    private String duracao;
    private String cartaz;

    @ManyToOne
    private ClassificacaoIndicativa classificacaoIndicativa;
}
