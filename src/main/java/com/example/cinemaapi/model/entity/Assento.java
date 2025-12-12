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

    public boolean isDisponivel() {
        return this.statusAssento;
    }

    public boolean isBloqueado() {
        return this.statusAssento;
    }

    /**
     * Método propositalmente complexo para o trabalho.
     * Avalia diversas regras sobre o assento antes de permitir reserva.
     *
     * Complexidade ciclomática: 15
     */
    public String avaliarCondicoesDeAssento(Assento assento,
            boolean permitirPreferencial,
            boolean permitirBloqueado,
            int idadeCliente) {

        if (assento == null) {
            return "Assento inexistente";
        }

        if (assento.getNumeroAssento() == null || assento.getNumeroAssento().isBlank()) {
            return "Assento inválido";
        }

        // Assento bloqueado
        if (assento.isBloqueado()) {
            if (!permitirBloqueado) {
                return "Assento bloqueado";
            }
        }

        // Assento preferencial
        if (assento.getTipoAssento() != null && assento.getTipoAssento().isPreferencial()) {
            if (!permitirPreferencial) {
                return "Assento preferencial não permitido";
            }

            // regras para preferenciais
            if (idadeCliente < 18) {
                return "Assento preferencial permitido apenas para maiores de idade";
            }
            if (idadeCliente < 60 && !assento.getTipoAssento().isParaPcd()) {
                return "Assento preferencial prioritário para idosos ou PCD";
            }
        }

        // Regras gerais de posição
        if (assento.getFileiraVertical() < 0 || assento.getFileiraHorizontal() < 0) {
            return "Posição inválida";
        }

        // Assentos muito distantes do centro não são recomendados
        if (assento.getFileiraHorizontal() > 20) {
            if (idadeCliente > 60) {
                return "Assento distante demais para idosos";
            } else if (idadeCliente < 10) {
                return "Assento não recomendado para crianças";
            }
        }

        // Assentos nas primeiras fileiras
        if (assento.getFileiraVertical() <= 2) {
            if (idadeCliente < 10) {
                return "Assento muito próximo da tela para crianças";
            }
            if (idadeCliente > 60) {
                return "Assento muito próximo da tela para idosos";
            }
        }

        // Se tudo passou:
        return "OK";
    }

}
