package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.CompraDTO;
import com.example.cinemaapi.api.dto.FilmeDTO;
import com.example.cinemaapi.model.entity.ClassificacaoIndicativa;
import com.example.cinemaapi.model.entity.Cliente;
import com.example.cinemaapi.model.entity.Compra;
import com.example.cinemaapi.model.entity.Filme;
import com.example.cinemaapi.service.ClassificacaoIndicativaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/filmes")
@RequiredArgsConstructor
@CrossOrigin
public class FilmeController {

    private ClassificacaoIndicativaService classificacaoIndicativaService;

    public Filme converter(FilmeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Filme filme = modelMapper.map(dto, Filme.class);
        if (dto.getIdClassificacaoIndicativa() != null) {
            Optional<ClassificacaoIndicativa> classificacaoIndicativa = classificacaoIndicativaService.getClassificacaoIndicativaById(dto.getIdClassificacaoIndicativa());
            if (!classificacaoIndicativa.isPresent()) {
                filme.setClassificacaoIndicativa(null);
            } else {
                filme.setClassificacaoIndicativa(classificacaoIndicativa.get());
            }
        }
        return filme;
    }
}
