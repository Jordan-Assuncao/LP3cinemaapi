package com.example.cinemaapi.model.repository;

import com.example.cinemaapi.model.entity.Ingresso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngressoRepository extends JpaRepository<Ingresso, Long> {
}
