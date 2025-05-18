package com.example.cinemaapi.service;

import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.TipoExibicao;
import com.example.cinemaapi.model.repository.TipoExibicaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TipoExibicaoService {

    private TipoExibicaoRepository repository;

    public TipoExibicaoService(TipoExibicaoRepository repository) {
        this.repository = repository;
    }

    public List<TipoExibicao> getTipoExibicaos() {
        return repository.findAll();
    }

    public Optional<TipoExibicao> getTipoExibicaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public TipoExibicao salvar(TipoExibicao tipoExibicao) {
        validar(tipoExibicao);
        return repository.save(tipoExibicao);
    }

    @Transactional
    public void excluir(TipoExibicao tipoeExibicao) {
        Objects.requireNonNull(tipoeExibicao.getId());
        repository.delete(tipoeExibicao);
    }

    public void validar(TipoExibicao tipoExibicao) {
        if (tipoExibicao.getFormatoExibicao() == null || tipoExibicao.getFormatoExibicao().trim().isEmpty()) {
            throw new RegraNegocioException("Formato de exibição inválido");
        }
        if (tipoExibicao.getDescricao() == null || tipoExibicao.getDescricao().trim().isEmpty()) {
            throw new RegraNegocioException("Descrição da exibição inválida");
        }
    }
}
