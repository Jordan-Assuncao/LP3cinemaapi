package com.example.cinemaapi.model.repository;

import com.example.cinemaapi.model.entity.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {
}
