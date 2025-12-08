package com.example.cinemaapi.model.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataCompra;
    private BigDecimal valorTotal;

    @ManyToOne
    private Cliente cliente;

    public void aplicarDesconto(BigDecimal desconto) {
        if (desconto == null || valorTotal == null)
            return;

        this.valorTotal = this.valorTotal.subtract(desconto);

        if (this.valorTotal.compareTo(BigDecimal.ZERO) < 0) {
            this.valorTotal = BigDecimal.ZERO;
        }
    }

    public void adicionarValor(BigDecimal valor) {
        if (valor != null) {
            if (this.valorTotal == null) {
                this.valorTotal = BigDecimal.ZERO;
            }
            this.valorTotal = this.valorTotal.add(valor);
        }
    }

    public boolean hasCliente() {
        return this.cliente != null;
    }

    public boolean isGratuita() {
        return valorTotal != null && valorTotal.compareTo(BigDecimal.ZERO) == 0;
    }

    public void tornarGratuita() {
        this.valorTotal = BigDecimal.ZERO;
    }
}
