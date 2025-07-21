package com.example.cinemaapi.model.repository;

import com.example.cinemaapi.model.entity.Genero;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneroRepository extends JpaRepository<Genero, Long> {

    List<Genero> findByIdIn(List<Long> ids);
}
