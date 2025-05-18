package com.example.cinemaapi.service;

import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Sala;
import com.example.cinemaapi.model.repository.SalaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SalaService {

    private SalaRepository repository;

    public SalaService(SalaRepository repository) {
        this.repository = repository;
    }

    public List<Sala> getSalas() {
        return repository.findAll();
    }

    public Optional<Sala> getSalaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Sala salvar(Sala sala) {
        validar(sala);
        return repository.save(sala);
    }

    @Transactional
    public void excluir(Sala sala) {
        Objects.requireNonNull(sala.getId());
        repository.delete(sala);
    }

    public void validar(Sala sala) {
        if (sala.getNumeroSala() == null || sala.getNumeroSala().trim().isEmpty()) {
            throw new RegraNegocioException("Número da sala inválido");
        }
        if (sala.getCapacidade() <= 0) {
            throw new RegraNegocioException("Capacidade da sala deve ser maior que zero");
        }
        if (sala.getCapacidadePreferencial() < 0) {
            throw new RegraNegocioException("Capacidade preferencial não pode ser negativa");
        }
        if (sala.getFormatoSala() == null || sala.getFormatoSala().trim().isEmpty()) {
            throw new RegraNegocioException("Formato da sala inválido");
        }
        if (sala.getNumeroFileiraVertical() <= 0) {
            throw new RegraNegocioException("Número de fileira vertical inválido");
        }
        if (sala.getNumeroFileiraHorizontal() <= 0) {
            throw new RegraNegocioException("Número de fileira horizontal inválido");
        }
        if (sala.getUnidade() == null || sala.getUnidade().getId() == null) {
            throw new RegraNegocioException("Unidade inválida");
        }
    }
}
