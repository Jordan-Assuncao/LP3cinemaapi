package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.FilmeGeneroDTO;
import com.example.cinemaapi.model.entity.Filme;
import com.example.cinemaapi.model.entity.FilmeGenero;
import com.example.cinemaapi.model.entity.Genero;
import com.example.cinemaapi.service.FilmeService;
import com.example.cinemaapi.service.GeneroService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/filmegeneros")
@RequiredArgsConstructor
@CrossOrigin
public class FilmeGeneroController {

    private final GeneroService generoService;
    private final FilmeService filmeService;

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
