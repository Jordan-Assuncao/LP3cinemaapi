package com.example.cinemaapi.service;

import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Preco;
import com.example.cinemaapi.model.repository.PrecoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PrecoService {

    private PrecoRepository repository;

    public PrecoService(PrecoRepository repository) {
        this.repository = repository;
    }

    public List<Preco> getPrecos() {
        return repository.findAll();
    }

    public Optional<Preco> getPrecoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Preco salvar(Preco preco) {
        validar(preco);
        return repository.save(preco);
    }

    @Transactional
    public void excluir(Preco preco) {
        Objects.requireNonNull(preco.getId());
        repository.delete(preco);
    }

    public void validar(Preco preco) {
        if (preco.getValorInteira() == null || preco.getValorInteira().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("Valor da inteira inválido");
        }
        if (preco.getDescricao() == null || preco.getDescricao().trim().isEmpty()) {
            throw new RegraNegocioException("Descrição do preço inválida");
        }
    }
}
