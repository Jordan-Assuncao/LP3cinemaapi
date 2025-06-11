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
import com.example.cinemaapi.api.dto.TipoAssentoDTO;
import com.example.cinemaapi.model.entity.TipoAssento;
import com.example.cinemaapi.service.TipoAssentoService;

@RestController
@RequestMapping("/api/v1/tipoassentos")
@RequiredArgsConstructor
@CrossOrigin
public class TipoAssentoController {

    private final TipoAssentoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<TipoAssento> tipoAssentos = service.getTipoAssentos();
        return ResponseEntity.ok(tipoAssentos.stream().map(TipoAssentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TipoAssento> tipoAssento = service.getTipoAssentoById(id);
        if (!tipoAssento.isPresent()) {
            return new ResponseEntity("Tipo assento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoAssento.map(TipoAssentoDTO::create));
    }

    public TipoAssento converter(TipoAssentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoAssento tipoAssento = modelMapper.map(dto, TipoAssento.class);
        return tipoAssento;
    }
}
