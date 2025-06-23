package com.example.cinemaapi.api.controller;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cinemaapi.api.dto.UnidadeDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Unidade;
import com.example.cinemaapi.service.UnidadeService;

@RestController
@RequestMapping("/api/v1/unidades")
@RequiredArgsConstructor
@CrossOrigin
public class UnidadeController {

    private final UnidadeService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Unidade> unidades = service.getUnidades();
        return ResponseEntity.ok(unidades.stream().map(UnidadeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Unidade> unidade = service.getUnidadeById(id);
        if (!unidade.isPresent()) {
            return new ResponseEntity("Unidade não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(unidade.map(UnidadeDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody UnidadeDTO dto) {
        try {
            Unidade unidade = converter(dto);
            unidade = service.salvar(unidade);
            return new ResponseEntity(unidade, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody UnidadeDTO dto) {
        if (!service.getUnidadeById(id).isPresent()) {
            return new ResponseEntity("Unidade não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Unidade unidade = converter(dto);
            unidade.setId(id);
            service.salvar(unidade);
            return ResponseEntity.ok(unidade);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Unidade converter(UnidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Unidade unidade = modelMapper.map(dto, Unidade.class);
        return unidade;
    }
}
