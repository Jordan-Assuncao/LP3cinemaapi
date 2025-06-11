package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.AssentoDTO;
import com.example.cinemaapi.model.entity.Assento;
import com.example.cinemaapi.model.entity.Sala;
import com.example.cinemaapi.model.entity.TipoAssento;
import com.example.cinemaapi.service.AssentoService;
import com.example.cinemaapi.service.SalaService;
import com.example.cinemaapi.service.TipoAssentoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/assentos")
@RequiredArgsConstructor
@CrossOrigin
public class AssentoController {

    private final TipoAssentoService tipoAssentoService;
    private final SalaService salaService;
    private final AssentoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Assento> assentos = service.getAssentos();
        return ResponseEntity.ok(assentos.stream().map(AssentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Assento> assento = service.getAssentoById(id);
        if (!assento.isPresent()) {
            return new ResponseEntity("Assento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(assento.map(AssentoDTO::create));
    }

    public Assento converter(AssentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Assento assento = modelMapper.map(dto, Assento.class);
        if (dto.getIdTipoAssento() != null) {
            Optional<TipoAssento> tipoAssento = tipoAssentoService.getTipoAssentoById(dto.getIdTipoAssento());
            if (!tipoAssento.isPresent()) {
                assento.setTipoAssento(null);
            } else {
                assento.setTipoAssento(tipoAssento.get());
            }
        }
        if (dto.getIdSala() != null) {
            Optional<Sala> sala = salaService.getSalaById(dto.getIdSala());
            if (!sala.isPresent()) {
                assento.setSala(null);
            } else {
                assento.setSala(sala.get());
            }
        }
        return assento;
    }
}
