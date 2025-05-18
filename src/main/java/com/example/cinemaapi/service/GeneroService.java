package com.example.cinemaapi.service;

import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Genero;
import com.example.cinemaapi.model.repository.GeneroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GeneroService {

    private GeneroRepository repository;

    public GeneroService(GeneroRepository repository) {
        this.repository = repository;
    }

    public List<Genero> getGeneros() {
        return repository.findAll();
    }

    public Optional<Genero> getGeneroById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Genero salvar(Genero genero) {
        validar(genero);
        return repository.save(genero);
    }

    @Transactional
    public void excluir(Genero genero) {
        Objects.requireNonNull(genero.getId());
        repository.delete(genero);
    }

    public void validar(Genero genero) {
        if (genero.getNomeGenero() == null || genero.getNomeGenero().trim().isEmpty()) {
            throw new RegraNegocioException("Nome do gênero inválido");
        }
        if (genero.getDescricao() == null || genero.getDescricao().trim().isEmpty()) {
            throw new RegraNegocioException("Descrição do gênero inválida");
        }
    }
}
