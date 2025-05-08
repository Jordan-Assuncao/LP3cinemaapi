package com.example.cinemaapi.model.repository;

import com.example.cinemaapi.model.entity.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneroRepository extends JpaRepository<Genero, Long> {
}
