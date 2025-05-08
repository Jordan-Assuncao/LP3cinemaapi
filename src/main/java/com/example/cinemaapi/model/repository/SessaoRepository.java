package com.example.cinemaapi.model.repository;

import com.example.cinemaapi.model.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {
}
