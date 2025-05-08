package com.example.cinemaapi.model.repository;

import com.example.cinemaapi.model.entity.Assento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssentoRepository extends JpaRepository<Assento, Long> {
}
