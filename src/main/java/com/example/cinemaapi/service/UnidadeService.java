package com.example.cinemaapi.service;

import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Unidade;
import com.example.cinemaapi.model.repository.UnidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UnidadeService {

    private UnidadeRepository repository;

    public UnidadeService(UnidadeRepository repository) {
        this.repository = repository;
    }

    public List<Unidade> getUnidades() {
        return repository.findAll();
    }

    public Optional<Unidade> getUnidadeById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Unidade salvar(Unidade unidade) {
        validar(unidade);
        return repository.save(unidade);
    }

    @Transactional
    public void excluir(Unidade unidade) {
        Objects.requireNonNull(unidade.getId());
        repository.delete(unidade);
    }

    public void validar(Unidade unidade) {
        if (unidade.getNomeUnidade() == null || unidade.getNomeUnidade().trim().isEmpty()) {
            throw new RegraNegocioException("Nome da unidade inválido");
        }
        if (unidade.getCnpj() == null || unidade.getCnpj().trim().isEmpty()) {
            throw new RegraNegocioException("CNPJ inválido");
        }
        if (unidade.getEmail() == null || unidade.getEmail().trim().isEmpty()) {
            throw new RegraNegocioException("Email inválido");
        }
        if (unidade.getTelefone() == null || unidade.getTelefone().trim().isEmpty()) {
            throw new RegraNegocioException("Telefone inválido");
        }
        if (unidade.getLogradouro() == null || unidade.getLogradouro().trim().isEmpty()) {
            throw new RegraNegocioException("Logradouro inválido");
        }
        if (unidade.getNumero() == null || unidade.getNumero().trim().isEmpty()) {
            throw new RegraNegocioException("Número inválido");
        }
        if (unidade.getBairro() == null || unidade.getBairro().trim().isEmpty()) {
            throw new RegraNegocioException("Bairro inválido");
        }
        if (unidade.getCidade() == null || unidade.getCidade().trim().isEmpty()) {
            throw new RegraNegocioException("Cidade inválida");
        }
        if (unidade.getEstado() == null || unidade.getEstado().trim().isEmpty()) {
            throw new RegraNegocioException("Estado inválido");
        }
        if (unidade.getCep() == null || unidade.getCep().trim().isEmpty()) {
            throw new RegraNegocioException("CEP inválido");
        }
    }
}
