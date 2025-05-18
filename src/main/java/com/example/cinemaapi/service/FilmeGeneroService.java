package com.example.cinemaapi.service;

import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.FilmeGenero;
import com.example.cinemaapi.model.repository.FilmeGeneroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FilmeGeneroService {

    private FilmeGeneroRepository repository;

    public FilmeGeneroService(FilmeGeneroRepository repository) {
        this.repository = repository;
    }

    public List<FilmeGenero> getFilmeGeneros() {
        return repository.findAll();
    }

    public Optional<FilmeGenero> getFilmeGeneroById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public FilmeGenero salvar(FilmeGenero filmeGenero) {
        validar(filmeGenero);
        return repository.save(filmeGenero);
    }

    @Transactional
    public void excluir(FilmeGenero filmeGenero) {
        Objects.requireNonNull(filmeGenero.getId());
        repository.delete(filmeGenero);
    }

    public void validar(FilmeGenero filmeGenero) {
        if (filmeGenero.getFilme() == null || filmeGenero.getFilme().getId() == null) {
            throw new RegraNegocioException("Filme inválido");
        }
        if (filmeGenero.getGenero() == null || filmeGenero.getGenero().getId() == null) {
            throw new RegraNegocioException("Gênero inválido");
        }
    }
}
