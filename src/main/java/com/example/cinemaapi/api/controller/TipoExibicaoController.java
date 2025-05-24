package com.example.cinemaapi.api.controller;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    public TipoExibicao converter(TipoExibicaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoExibicao tipoExibicao = modelMapper.map(dto, TipoExibicao.class);
        return tipoExibicao;
    }
}
