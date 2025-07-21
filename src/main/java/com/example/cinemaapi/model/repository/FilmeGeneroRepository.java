package com.example.cinemaapi.model.repository;

import com.example.cinemaapi.model.entity.Filme;
import com.example.cinemaapi.model.entity.FilmeGenero;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FilmeGeneroRepository extends JpaRepository<FilmeGenero, Long> {

    List<FilmeGenero> findByFilme(Filme filme);

    @Query("SELECT fg FROM FilmeGenero fg WHERE fg.filme.id = :filmeId")
    List<FilmeGenero> findByFilmeId(@Param("filmeId") Long filmeId);

    List<FilmeGenero> findAllByFilmeId(Long idFilme);
}
