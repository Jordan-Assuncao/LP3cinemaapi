package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.ClassificacaoIndicativaDTO;
import com.example.cinemaapi.model.entity.ClassificacaoIndicativa;
import com.example.cinemaapi.service.ClassificacaoIndicativaService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ClassificacaoIndicativa> classificacaoIndicativa = service.getClassificacaoIndicativaById(id);
        if (!classificacaoIndicativa.isPresent()) {
            return new ResponseEntity("Classificação indicativa não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(classificacaoIndicativa.map(ClassificacaoIndicativaDTO::create));
    }

    public ClassificacaoIndicativa converter(ClassificacaoIndicativaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ClassificacaoIndicativa classificacaoIndicativa = modelMapper.map(dto, ClassificacaoIndicativa.class);
        return classificacaoIndicativa;
    }
}
