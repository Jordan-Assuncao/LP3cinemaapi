package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.SessaoDTO;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cinemaapi.api.dto.TipoExibicaoDTO;
import com.example.cinemaapi.model.entity.TipoExibicao;
import com.example.cinemaapi.service.TipoExibicaoService;

@RestController
@RequestMapping("/api/v1/tipoexibicoes")
@RequiredArgsConstructor
@CrossOrigin
public class TipoExibicaoController {

    private final TipoExibicaoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<TipoExibicao> tipoExibicaos = service.getTipoExibicaos();
        return ResponseEntity.ok(tipoExibicaos.stream().map(TipoExibicaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TipoExibicao> tipoExibicao = service.getTipoExibicaoById(id);
        if (!tipoExibicao.isPresent()) {
            return new ResponseEntity("Tipo exibição não encontrada.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoExibicao.map(TipoExibicaoDTO::create));
    }

    public TipoExibicao converter(TipoExibicaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoExibicao tipoExibicao = modelMapper.map(dto, TipoExibicao.class);
        return tipoExibicao;
    }
}
