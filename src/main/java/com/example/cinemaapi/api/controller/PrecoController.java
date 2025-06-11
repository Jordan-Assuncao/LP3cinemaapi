package com.example.cinemaapi.api.controller;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cinemaapi.api.dto.PrecoDTO;
import com.example.cinemaapi.model.entity.Preco;
import com.example.cinemaapi.service.PrecoService;

@RestController
@RequestMapping("/api/v1/precos")
@RequiredArgsConstructor
@CrossOrigin
public class PrecoController {

    private final PrecoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Preco> precos = service.getPrecos();
        return ResponseEntity.ok(precos.stream().map(PrecoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Preco> preco = service.getPrecoById(id);
        if (!preco.isPresent()) {
            return new ResponseEntity("Preço não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(preco.map(PrecoDTO::create));
    }

    public Preco converter(PrecoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Preco preco = modelMapper.map(dto, Preco.class);
        return preco;
    }
}
