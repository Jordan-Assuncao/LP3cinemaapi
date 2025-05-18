package com.example.cinemaapi.service;

import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.SessaoTipoExibicao;
import com.example.cinemaapi.model.repository.SessaoTipoExibicaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SessaoTipoExibicaoService {

    private SessaoTipoExibicaoRepository repository;

    public SessaoTipoExibicaoService(SessaoTipoExibicaoRepository repository) {
        this.repository = repository;
    }

    public List<SessaoTipoExibicao> getSessaoTipoExibicaos() {
        return repository.findAll();
    }

    public Optional<SessaoTipoExibicao> getSessaoTipoExibicaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public SessaoTipoExibicao salvar(SessaoTipoExibicao sessaoTipoExibicao) {
        validar(sessaoTipoExibicao);
        return repository.save(sessaoTipoExibicao);
    }

    @Transactional
    public void excluir(SessaoTipoExibicao sessaoTipoExibicao) {
        Objects.requireNonNull(sessaoTipoExibicao.getId());
        repository.delete(sessaoTipoExibicao);
    }

    public void validar(SessaoTipoExibicao sessaoTipoExibicao) {
        if (sessaoTipoExibicao.getSessao() == null || sessaoTipoExibicao.getSessao().getId() == null) {
            throw new RegraNegocioException("Sessão inválida");
        }
        if (sessaoTipoExibicao.getTipoExibicao() == null || sessaoTipoExibicao.getTipoExibicao().getId() == null) {
            throw new RegraNegocioException("Tipo de exibição inválido");
        }
    }
}
