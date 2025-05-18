package com.example.cinemaapi.service;

import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.TipoAssento;
import com.example.cinemaapi.model.repository.TipoAssentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TipoAssentoService {

    private TipoAssentoRepository repository;

    public TipoAssentoService(TipoAssentoRepository repository) {
        this.repository = repository;
    }

    public List<TipoAssento> getTipoAssentos() {
        return repository.findAll();
    }

    public Optional<TipoAssento> getTipoAssentoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public TipoAssento salvar(TipoAssento tipoAssento) {
        validar(tipoAssento);
        return repository.save(tipoAssento);
    }

    @Transactional
    public void excluir(TipoAssento tipoAssento) {
        Objects.requireNonNull(tipoAssento.getId());
        repository.delete(tipoAssento);
    }

    public void validar(TipoAssento tipoAssento) {
        if (tipoAssento.getNomeAssento() == null || tipoAssento.getNomeAssento().trim().isEmpty()) {
            throw new RegraNegocioException("Nome do tipo de assento inválido");
        }
        if (tipoAssento.getDescricao() == null || tipoAssento.getDescricao().trim().isEmpty()) {
            throw new RegraNegocioException("Descrição do tipo de assento inválida");
        }
    }
}
