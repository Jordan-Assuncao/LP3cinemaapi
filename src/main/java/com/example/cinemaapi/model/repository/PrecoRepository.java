package com.example.cinemaapi.model.repository;

import com.example.cinemaapi.model.entity.Preco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrecoRepository extends JpaRepository<Preco, Long> {
}
