package com.example.cinemaapi.api.controller;

import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.cinemaapi.api.dto.SessaoDTO;
import com.example.cinemaapi.model.entity.Filme;
import com.example.cinemaapi.model.entity.Preco;
import com.example.cinemaapi.model.entity.Sala;
import com.example.cinemaapi.model.entity.Sessao;
import com.example.cinemaapi.service.FilmeService;
import com.example.cinemaapi.service.PrecoService;
import com.example.cinemaapi.service.SalaService;

@RestController
@RequestMapping("/api/v1/sessoes")
@RequiredArgsConstructor
@CrossOrigin
public class SessaoController {

    private final FilmeService filmeService;
    private final PrecoService precoService;
    private final SalaService salaService;

    public Sessao converter(SessaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Sessao sessao = modelMapper.map(dto, Sessao.class);
        if (dto.getIdFilme() != null) {
            Optional<Filme> filme = filmeService.getFilmeById(dto.getIdFilme());
            if (!filme.isPresent()) {
                sessao.setFilme(null);
            } else {
                sessao.setFilme(filme.get());
            }
        }
        if (dto.getIdPreco() != null) {
            Optional<Preco> preco = precoService.getPrecoById(dto.getIdPreco());
            if (!preco.isPresent()) {
                sessao.setPreco(null);
            } else {
                sessao.setPreco(preco.get());
            }
        }
        if (dto.getIdSala() != null) {
            Optional<Sala> sala = salaService.getSalaById(dto.getIdSala());
            if (!sala.isPresent()) {
                sessao.setSala(null);
            } else {
                sessao.setSala(sala.get());
            }
        }
        return sessao;
    }
}
