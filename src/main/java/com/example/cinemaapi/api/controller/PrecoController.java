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

    public Preco converter(PrecoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Preco preco = modelMapper.map(dto, Preco.class);
        return preco;
    }
}
