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

    public int getCapacidadeTotal() {
        return capacidade + capacidadePreferencial;
    }

    public boolean possuiAssentosPreferenciais() {
        return capacidadePreferencial > 0;
    }

    public int getTotalPosicoesGrid() {
        return numeroFileiraVertical * numeroFileiraHorizontal;
    }

    public boolean isCapacidadeCompatível() {
        return getCapacidadeTotal() <= getTotalPosicoesGrid();
    }

    public double getPercentualPreferenciais() {
        if (getCapacidadeTotal() == 0)
            return 0;
        return (double) capacidadePreferencial / getCapacidadeTotal();
    }

    public double getDensidadeAssentos() {
        if (getTotalPosicoesGrid() == 0)
            return 0;
        return (double) getCapacidadeTotal() / getTotalPosicoesGrid();
    }


    /**
     * Método que realiza validação de regras comerciais (domínio simplificado para utilização na tarefa)
     * Pontos de Decisão (P): 10 / Cálculo: Ponto do início do método + (P) = 11 / Complexidade Ciclomática (CC): 11*/
    public String validarPoliticasComerciais() {
        StringBuilder erros = new StringBuilder();
        if (capacidade < 10) {
            erros.append("Capacidade mínima não atingida.\n");
        }
        if (capacidade > 150) {
            erros.append("Capacidade máxima excedida.\n");
        }
        if (capacidadePreferencial > 0 && capacidade < 20) {
            // P4: && capacidade < 20
            erros.append("Sala pequena demais para assentos preferenciais.\n");
        }
        double densidade = getDensidadeAssentos();
        if (densidade < 0.3) {
            erros.append("Densidade de assentos muito baixa.\n");
        }
        else if (densidade > 0.95) {
            erros.append("Densidade de assentos muito alta.\n");
        }
        if (numeroFileiraVertical <= 2 || numeroFileiraHorizontal <= 2) {
            // P8: ||
            erros.append("Número insuficiente de fileiras.\n");
        }
        if (unidade == null) {
            erros.append("Sala sem unidade vinculada.\n");
        }
        if (erros.length() == 0) {
            return "Políticas comerciais validadas com sucesso.";
        }
        return erros.toString();
    }
}