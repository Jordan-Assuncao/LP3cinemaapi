package com.example.cinemaapi.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Preco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valorInteira;
    private String descricao;

    public BigDecimal getMeiaEntrada() {
        if (valorInteira == null)
            return BigDecimal.ZERO;
        return valorInteira.divide(BigDecimal.valueOf(2));
    }

    public boolean isValido() {
        return valorInteira != null && valorInteira.compareTo(BigDecimal.ZERO) > 0;
    }
}
