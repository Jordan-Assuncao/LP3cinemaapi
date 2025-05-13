package com.example.cinemaapi.service;

import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.ClassificacaoIndicativa;
import com.example.cinemaapi.model.repository.ClassificacaoIndicativaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClassificacaoIndicativaService {

    private ClassificacaoIndicativaRepository repository;

    public ClassificacaoIndicativaService(ClassificacaoIndicativaRepository repository) {
        this.repository = repository;
    }

    public List<ClassificacaoIndicativa> getClassificacaoIndicativas() {
        return repository.findAll();
    }

    public Optional<ClassificacaoIndicativa> getClassificacaoIndicativaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ClassificacaoIndicativa salvar(ClassificacaoIndicativa classificacaoIndicativa) {
        validar(classificacaoIndicativa);
        return repository.save(classificacaoIndicativa);
    }

    @Transactional
    public void excluir(ClassificacaoIndicativa classificacaoIndicativa) {
        Objects.requireNonNull(classificacaoIndicativa.getId());
        repository.delete(classificacaoIndicativa);
    }

    public void validar(ClassificacaoIndicativa classificacaoIndicativa) {
        if (classificacaoIndicativa.getFaixaEtaria() == null) {
            throw new RegraNegocioException("Faixa etária inválida");
        }
    }
}
