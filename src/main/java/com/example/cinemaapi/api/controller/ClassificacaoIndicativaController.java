package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.ClassificacaoIndicativaDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
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

    @PostMapping()
    public ResponseEntity post(@RequestBody ClassificacaoIndicativaDTO dto) {
        try {
            ClassificacaoIndicativa classificacaoIndicativa = converter(dto);
            classificacaoIndicativa = service.salvar(classificacaoIndicativa);
            return new ResponseEntity(classificacaoIndicativa, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ClassificacaoIndicativaDTO dto) {
        if (!service.getClassificacaoIndicativaById(id).isPresent()) {
            return new ResponseEntity("Classificação indicativa não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            ClassificacaoIndicativa classificacaoIndicativa = converter(dto);
            classificacaoIndicativa.setId(id);
            service.salvar(classificacaoIndicativa);
            return ResponseEntity.ok(classificacaoIndicativa);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ClassificacaoIndicativa converter(ClassificacaoIndicativaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ClassificacaoIndicativa classificacaoIndicativa = modelMapper.map(dto, ClassificacaoIndicativa.class);
        return classificacaoIndicativa;
    }
}
