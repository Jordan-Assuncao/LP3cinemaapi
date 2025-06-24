package com.example.cinemaapi.api.controller;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cinemaapi.api.dto.TipoAssentoDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
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

    @PostMapping()
    public ResponseEntity post(@RequestBody TipoAssentoDTO dto) {
        try {
            TipoAssento tipoAssento = converter(dto);
            tipoAssento = service.salvar(tipoAssento);
            return new ResponseEntity(tipoAssento, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TipoAssentoDTO dto) {
        if (!service.getTipoAssentoById(id).isPresent()) {
            return new ResponseEntity("Tipo Assento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TipoAssento tipoAssento = converter(dto);
            tipoAssento.setId(id);
            service.salvar(tipoAssento);
            return ResponseEntity.ok(tipoAssento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TipoAssento> tipoAssento = service.getTipoAssentoById(id);
        if (!tipoAssento.isPresent()) {
            return new ResponseEntity("Tipo Assento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tipoAssento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public TipoAssento converter(TipoAssentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoAssento tipoAssento = modelMapper.map(dto, TipoAssento.class);
        return tipoAssento;
    }
}
