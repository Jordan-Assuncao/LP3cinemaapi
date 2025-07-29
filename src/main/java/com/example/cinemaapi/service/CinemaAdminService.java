package com.example.cinemaapi.service;

import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.CinemaAdmin;
import com.example.cinemaapi.model.repository.CinemaAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CinemaAdminService {

    private CinemaAdminRepository repository;

    public CinemaAdminService(CinemaAdminRepository repository) {
        this.repository = repository;
    }

    public List<CinemaAdmin> getCinemaAdmins() {
        return repository.findAll();
    }

    public Optional<CinemaAdmin> getCinemaAdminById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public CinemaAdmin salvar(CinemaAdmin cinemaAdmin) {
        validar(cinemaAdmin);
        return repository.save(cinemaAdmin);
    }

    @Transactional
    public void excluir(CinemaAdmin cinemaAdmin) {
        Objects.requireNonNull(cinemaAdmin.getId());
        repository.delete(cinemaAdmin);
    }

    public void validar(CinemaAdmin cinemaAdmin) {
        if (cinemaAdmin.getNomeCinema() == null || cinemaAdmin.getNomeCinema().trim().isEmpty()) {
            throw new RegraNegocioException("Nome do Cinema inválido");
        }
        if (cinemaAdmin.getEmail() == null || cinemaAdmin.getEmail().trim().isEmpty()) {
            throw new RegraNegocioException("Email inválido");
        }
        if (cinemaAdmin.getSenha() == null || cinemaAdmin.getSenha().trim().isEmpty()) {
            throw new RegraNegocioException("Senha inválida");
        }
    }
}
