package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.SessaoDTO;
import com.example.cinemaapi.model.entity.Sessao;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cinemaapi.api.dto.UnidadeDTO;
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

    public Unidade converter(UnidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Unidade unidade = modelMapper.map(dto, Unidade.class);
        return unidade;
    }
}
