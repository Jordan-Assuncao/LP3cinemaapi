package com.example.cinemaapi.api.controller;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cinemaapi.api.dto.SessaoDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Filme;
import com.example.cinemaapi.model.entity.Preco;
import com.example.cinemaapi.model.entity.Sala;
import com.example.cinemaapi.model.entity.Sessao;
import com.example.cinemaapi.service.FilmeService;
import com.example.cinemaapi.service.PrecoService;
import com.example.cinemaapi.service.SalaService;
import com.example.cinemaapi.service.SessaoService;

@RestController
@RequestMapping("/api/v1/sessoes")
@RequiredArgsConstructor
@CrossOrigin
public class SessaoController {

    private final FilmeService filmeService;
    private final PrecoService precoService;
    private final SalaService salaService;
    private final SessaoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Sessao> sessaos = service.getSessaos();
        return ResponseEntity.ok(sessaos.stream().map(SessaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Sessao> sessao = service.getSessaoById(id);
        if (!sessao.isPresent()) {
            return new ResponseEntity("Sessão não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sessao.map(SessaoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody SessaoDTO dto) {
        try {
            Sessao sessao = converter(dto);
            sessao = service.salvar(sessao);
            return new ResponseEntity(sessao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody SessaoDTO dto) {
        if (!service.getSessaoById(id).isPresent()) {
            return new ResponseEntity("Sessao não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Sessao sessao = converter(dto);
            sessao.setId(id);
            service.salvar(sessao);
            return ResponseEntity.ok(sessao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Sessao> sessao = service.getSessaoById(id);
        if (!sessao.isPresent()) {
            return new ResponseEntity("Sessão não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(sessao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Sessao converter(SessaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Sessao sessao = modelMapper.map(dto, Sessao.class);
        if (dto.getIdFilme() != null) {
            Optional<Filme> filme = filmeService.getFilmeById(dto.getIdFilme());
            if (!filme.isPresent()) {
                sessao.setFilme(null);
            } else {
                sessao.setFilme(filme.get());
            }
        }
        if (dto.getIdPreco() != null) {
            Optional<Preco> preco = precoService.getPrecoById(dto.getIdPreco());
            if (!preco.isPresent()) {
                sessao.setPreco(null);
            } else {
                sessao.setPreco(preco.get());
            }
        }
        if (dto.getIdSala() != null) {
            Optional<Sala> sala = salaService.getSalaById(dto.getIdSala());
            if (!sala.isPresent()) {
                sessao.setSala(null);
            } else {
                sessao.setSala(sala.get());
            }
        }
        return sessao;
    }
}
