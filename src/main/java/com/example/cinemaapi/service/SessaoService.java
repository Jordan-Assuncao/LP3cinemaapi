package com.example.cinemaapi.service;

import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Sessao;
import com.example.cinemaapi.model.repository.SessaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SessaoService {

    private SessaoRepository repository;

    public SessaoService(SessaoRepository repository) {
        this.repository = repository;
    }

    public List<Sessao> getSessaos() {
        return repository.findAll();
    }

    public Optional<Sessao> getSessaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Sessao salvar(Sessao sessao) {
        validar(sessao);
        return repository.save(sessao);
    }

    @Transactional
    public void excluir(Sessao sessao) {
        Objects.requireNonNull(sessao.getId());
        repository.delete(sessao);
    }

    public void validar(Sessao sessao) {
        if (sessao.getDataSessao() == null || sessao.getDataSessao().trim().isEmpty()) {
            throw new RegraNegocioException("Data da sessão inválida");
        }
        if (sessao.getHoraSessao() == null || sessao.getHoraSessao().trim().isEmpty()) {
            throw new RegraNegocioException("Hora da sessão inválida");
        }
        if (sessao.getSala() == null || sessao.getSala().getId() == null) {
            throw new RegraNegocioException("Sala inválida");
        }
        if (sessao.getFilme() == null || sessao.getFilme().getId() == null) {
            throw new RegraNegocioException("Filme inválido");
        }
        if (sessao.getPreco() == null || sessao.getPreco().getId() == null) {
            throw new RegraNegocioException("Preço inválido");
        }
    }
}
