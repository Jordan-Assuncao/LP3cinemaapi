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
public class ClassificacaoIndicativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String faixaEtaria;
    private String descricao;

    public boolean isLivre() {
        return "L".equalsIgnoreCase(faixaEtaria) ||
               "Livre".equalsIgnoreCase(faixaEtaria);
    }

    public boolean isParaAdultos() {
        return "18".equals(faixaEtaria) ||
               "18+".equals(faixaEtaria) ||
               "Maior de 18".equalsIgnoreCase(faixaEtaria);
    }
}
