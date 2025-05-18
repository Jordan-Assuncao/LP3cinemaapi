package com.example.cinemaapi.service;

import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Filme;
import com.example.cinemaapi.model.repository.FilmeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FilmeService {

    private FilmeRepository repository;

    public FilmeService(FilmeRepository repository) {
        this.repository = repository;
    }

    public List<Filme> getFilmes() {
        return repository.findAll();
    }

    public Optional<Filme> getFilmeById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Filme salvar(Filme filme) {
        validar(filme);
        return repository.save(filme);
    }

    @Transactional
    public void excluir(Filme filme) {
        Objects.requireNonNull(filme.getId());
        repository.delete(filme);
    }

    public void validar(Filme filme) {
        if (filme.getTitulo() == null || filme.getTitulo().trim().isEmpty()) {
            throw new RegraNegocioException("Título do filme inválido");
        }
        if (filme.getSinopse() == null || filme.getSinopse().trim().isEmpty()) {
            throw new RegraNegocioException("Sinopse inválida");
        }
        if (filme.getDuracao() == null || filme.getDuracao().trim().isEmpty()) {
            throw new RegraNegocioException("Duração inválida");
        }
        if (filme.getCartaz() == null || filme.getCartaz().trim().isEmpty()) {
            throw new RegraNegocioException("Cartaz inválido");
        }
        if (filme.getClassificacaoIndicativa() == null || filme.getClassificacaoIndicativa().getId() == null) {
            throw new RegraNegocioException("Classificação indicativa inválida");
        }
    }
}
