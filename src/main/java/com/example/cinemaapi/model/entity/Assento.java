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
     * Complexidade ciclomática: 11
     */
    public String avaliarCondicoesDeAssento(Assento assento, boolean permitirPreferencial, boolean permitirBloqueado, int idadeCliente) {
        if (assento == null) {
            return "Assento inexistente";
        }
        if (assento.getNumeroAssento() == null) {
            return "Assento inválido";
        }
        if (assento.getNumeroAssento().isBlank()) {
            return "Assento inválido";
        }
        if (assento.isBloqueado()) {
            if (!permitirBloqueado) {
                return "Assento bloqueado";
            }
        }
        if (assento.getTipoAssento() != null) {
            if (assento.getTipoAssento().isPreferencial()) {
                if (!permitirPreferencial) {
                    return "Assento preferencial não permitido";
                }
                if (idadeCliente < 18) {
                    return "Assento preferencial permitido apenas para maiores de idade";
                }
                if (idadeCliente < 10) {
                    return "Assento preferencial: idade mínima de 10 anos";
                }
            }
        }
        return "OK";
    }
}
