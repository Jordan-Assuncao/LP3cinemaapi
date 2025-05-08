package com.example.cinemaapi.model.repository;

import com.example.cinemaapi.model.entity.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
}
