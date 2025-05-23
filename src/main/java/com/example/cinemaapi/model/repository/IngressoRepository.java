package com.example.cinemaapi.model.repository;

import com.example.cinemaapi.model.entity.Compra;
import com.example.cinemaapi.model.entity.Ingresso;
import com.example.cinemaapi.model.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngressoRepository extends JpaRepository<Ingresso, Long> {
    List<Ingresso> findBySessao(Sessao sessao);
    List<Ingresso> findByCompra(Compra compra);

}
