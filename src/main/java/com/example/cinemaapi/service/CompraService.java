package com.example.cinemaapi.service;

import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Compra;
import com.example.cinemaapi.model.repository.CompraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CompraService {

    private CompraRepository repository;

    public CompraService(CompraRepository repository) {
        this.repository = repository;
    }

    public List<Compra> getCompras() {
        return repository.findAll();
    }

    public Optional<Compra> getCompraById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Compra salvar(Compra compra) {
        validar(compra);
        return repository.save(compra);
    }

    @Transactional
    public void excluir(Compra compra) {
        Objects.requireNonNull(compra.getId());
        repository.delete(compra);
    }

    public void validar(Compra compra) {
        if (compra.getDataCompra() == null || compra.getDataCompra().trim().isEmpty()) {
            throw new RegraNegocioException("Data da compra inválida");
        }
        if (compra.getValorTotal() == null || compra.getValorTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("Valor total inválido");
        }
        if (compra.getCliente() == null || compra.getCliente().getId() == null) {
            throw new RegraNegocioException("Cliente inválido");
        }
    }
}
