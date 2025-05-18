package com.example.cinemaapi.service;

import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Ingresso;
import com.example.cinemaapi.model.repository.IngressoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class IngressoService {

    private IngressoRepository repository;

    public IngressoService(IngressoRepository repository) {
        this.repository = repository;
    }

    public List<Ingresso> getIngressos() {
        return repository.findAll();
    }

    public Optional<Ingresso> getIngressoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Ingresso salvar(Ingresso ingresso) {
        validar(ingresso);
        return repository.save(ingresso);
    }

    @Transactional
    public void excluir(Ingresso ingresso) {
        Objects.requireNonNull(ingresso.getId());
        repository.delete(ingresso);
    }

    public void validar(Ingresso ingresso) {
        if (ingresso.getSessao() == null || ingresso.getSessao().getId() == null) {
            throw new RegraNegocioException("Sessão inválida");
        }
        if (ingresso.getAssento() == null || ingresso.getAssento().getId() == null) {
            throw new RegraNegocioException("Assento inválido");
        }
        if (ingresso.getCompra() == null || ingresso.getCompra().getId() == null) {
            throw new RegraNegocioException("Compra inválida");
        }
    }
}
