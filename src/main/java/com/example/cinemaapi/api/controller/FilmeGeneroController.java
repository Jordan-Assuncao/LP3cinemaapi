package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.FilmeGeneroDTO;
import com.example.cinemaapi.model.entity.Filme;
import com.example.cinemaapi.model.entity.FilmeGenero;
import com.example.cinemaapi.model.entity.Genero;
import com.example.cinemaapi.service.FilmeGeneroService;
import com.example.cinemaapi.service.FilmeService;
import com.example.cinemaapi.service.GeneroService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/filmegeneros")
@RequiredArgsConstructor
@CrossOrigin
public class FilmeGeneroController {

    private final GeneroService generoService;
    private final FilmeService filmeService;
    private final FilmeGeneroService service;

    @GetMapping()
    public ResponseEntity get() {
        List<FilmeGenero> filmeGeneros = service.getFilmeGeneros();
        return ResponseEntity.ok(filmeGeneros.stream().map(FilmeGeneroDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<FilmeGenero> filmeGenero = service.getFilmeGeneroById(id);
        if (!filmeGenero.isPresent()) {
            return new ResponseEntity("Filme gênero não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(filmeGenero.map(FilmeGeneroDTO::create));
    }

    public FilmeGenero converter(FilmeGeneroDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        FilmeGenero filmeGenero = modelMapper.map(dto, FilmeGenero.class);
        if (dto.getIdGenero() != null) {
            Optional<Genero> genero = generoService.getGeneroById(dto.getIdGenero());
            if (!genero.isPresent()) {
                filmeGenero.setGenero(null);
            } else {
                filmeGenero.setGenero(genero.get());
            }
        }
        if (dto.getIdFilme() != null) {
            Optional<Filme> filme = filmeService.getFilmeById(dto.getIdFilme());
            if (!filme.isPresent()) {
                filmeGenero.setFilme(null);
            } else {
                filmeGenero.setFilme(filme.get());
            }
        }
        return filmeGenero;
    }
}
