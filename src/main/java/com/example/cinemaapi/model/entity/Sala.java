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
     * Método complexo complexidade ciclomática 15
     * para o trabalho.
     */
    public String validarConfiguracaoSala() {
        StringBuilder erros = new StringBuilder();

        if (numeroSala == null || numeroSala.trim().isEmpty()) {
            erros.append("Número da sala inválido.\n");
        }

        if (capacidade <= 0) {
            erros.append("A capacidade da sala deve ser maior que zero.\n");
        }

        if (capacidadePreferencial < 0) {
            erros.append("A capacidade preferencial não pode ser negativa.\n");
        }

        if (capacidadePreferencial > capacidade) {
            erros.append("Assentos preferenciais não podem exceder os assentos comuns.\n");
        }

        if (numeroFileiraVertical <= 0 || numeroFileiraHorizontal <= 0) {
            erros.append("Quantidade de fileiras inválida.\n");
        }

        if (getCapacidadeTotal() > getTotalPosicoesGrid()) {
            erros.append("Capacidade total excede o número de posições possíveis no grid.\n");
        }

        if (formatoSala == null || formatoSala.trim().isEmpty()) {
            erros.append("Formato da sala inválido.\n");
        } else {
            if (!formatoSala.equalsIgnoreCase("retangular")
                    && !formatoSala.equalsIgnoreCase("oval")
                    && !formatoSala.equalsIgnoreCase("imax")
                    && !formatoSala.equalsIgnoreCase("premium")) {
                erros.append("Formato da sala não reconhecido.\n");
            }
        }

        if (unidade == null) {
            erros.append("Sala não vinculada a nenhuma unidade.\n");
        }

        if (capacidade < 30 && formatoSala.equalsIgnoreCase("imax")) {
            erros.append("Uma sala IMAX não pode ter capacidade inferior a 30 lugares.\n");
        }

        if (capacidadePreferencial > 0 && capacidade < 20) {
            erros.append("Sala pequena demais para possuir assentos preferenciais.\n");
        }

        double densidade = getDensidadeAssentos();
        if (densidade < 0.3) {
            erros.append("Densidade de assentos muito baixa para uma sala comercial.\n");
        } else if (densidade > 0.95) {
            erros.append("Densidade de assentos muito alta e pode prejudicar mobilidade.\n");
        }

        if (erros.length() == 0) {
            return "Configuração da sala válida.";
        }

        return erros.toString();

    }
}