package com.example.cinemaapi.service;

import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Assento;
import com.example.cinemaapi.model.repository.AssentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AssentoService {

    private AssentoRepository repository;

    public AssentoService(AssentoRepository repository) {
        this.repository = repository;
    }

    public List<Assento> getAssentos() {
        return repository.findAll();
    }

    public Optional<Assento> getAssentoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Assento salvar(Assento assento) {
        validar(assento);
        return repository.save(assento);
    }

    @Transactional
    public void excluir(Assento assento) {
        Objects.requireNonNull(assento.getId());
        repository.delete(assento);
    }

    public void validar(Assento assento) {
        if (assento.getNumeroAssento() == null || assento.getNumeroAssento().trim().isEmpty()) {
            throw new RegraNegocioException("Número do assento inválido");
        }
        if (assento.getFileiraVertical() < 0) {
            throw new RegraNegocioException("Fileira vertical inválida");
        }
        if (assento.getFileiraHorizontal() < 0) {
            throw new RegraNegocioException("Fileira horizontal inválida");
        }
        if (assento.getSala() == null || assento.getSala().getId() == null) {
            throw new RegraNegocioException("Sala inválida");
        }
        if (assento.getTipoAssento() == null || assento.getTipoAssento().getId() == null) {
            throw new RegraNegocioException("Tipo de assento inválido");
        }
    }
}
