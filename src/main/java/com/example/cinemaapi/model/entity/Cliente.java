package com.example.cinemaapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String senha;

    public boolean isCpfValido() {
        return cpf != null && cpf.matches("\\d{11}");
    }

    public boolean isEmailValido() {
        return email != null && email.contains("@") && email.contains(".");
    }

    public boolean hasContatoCompleto() {
        return telefone != null && !telefone.isEmpty() &&
               email != null && !email.isEmpty();
    }

    public boolean isSenhaSegura() {
        return senha != null && senha.length() >= 6;
    }
}

