package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.ClassificacaoIndicativaDTO;
import com.example.cinemaapi.model.entity.ClassificacaoIndicativa;
import com.example.cinemaapi.service.ClassificacaoIndicativaService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/classificacaoindicativas")
@RequiredArgsConstructor
@CrossOrigin
public class ClassificacaoIndicativaController {

    private final ClassificacaoIndicativaService service;

    @GetMapping()
    public ResponseEntity get() {
        List<ClassificacaoIndicativa> classificacaoIndicativas = service.getClassificacaoIndicativas();
        return ResponseEntity.ok(classificacaoIndicativas.stream().map(ClassificacaoIndicativaDTO::create).collect(Collectors.toList()));
    }

    public ClassificacaoIndicativa converter(ClassificacaoIndicativaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ClassificacaoIndicativa classificacaoIndicativa = modelMapper.map(dto, ClassificacaoIndicativa.class);
        return classificacaoIndicativa;
    }
}
