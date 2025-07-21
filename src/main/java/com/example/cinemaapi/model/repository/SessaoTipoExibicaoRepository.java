package com.example.cinemaapi.model.repository;

import com.example.cinemaapi.model.entity.SessaoTipoExibicao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoTipoExibicaoRepository extends JpaRepository<SessaoTipoExibicao, Long> {

    List<SessaoTipoExibicao> findBySessaoId(Long sessaoId);
}
