package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.FilmeGeneroDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Filme;
import com.example.cinemaapi.model.entity.FilmeGenero;
import com.example.cinemaapi.model.entity.Genero;
import com.example.cinemaapi.model.repository.FilmeGeneroRepository;
import com.example.cinemaapi.service.FilmeGeneroService;
import com.example.cinemaapi.service.FilmeService;
import com.example.cinemaapi.service.GeneroService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/filmegeneros")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Filme Gêneros")
public class FilmeGeneroController {

    private final GeneroService generoService;
    private final FilmeService filmeService;
    private final FilmeGeneroService service;
    private final FilmeGeneroRepository filmeGeneroRepository;

    @GetMapping()
    @ApiOperation("Obter detalhes de Filme Gêneros")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Filme gêneros encontrados"),
            @ApiResponse(code = 404, message = "Filme gêneros não encontrados") })
    public ResponseEntity get() {
        List<FilmeGenero> filmeGeneros = service.getFilmeGeneros();
        return ResponseEntity.ok(filmeGeneros.stream().map(FilmeGeneroDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Filme Gênero")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Filme Gênero encontrado"),
            @ApiResponse(code = 404, message = "Filme Gênero não encontrado") })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<FilmeGenero> filmeGenero = service.getFilmeGeneroById(id);
        if (!filmeGenero.isPresent()) {
            return new ResponseEntity("Filme gênero não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(filmeGenero.map(FilmeGeneroDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo Filme Gênero")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Filme Gênero salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Filme Gênero") })
    public ResponseEntity post(@RequestBody FilmeGeneroDTO dto) {
        try {
            FilmeGenero filmeGenero = converter(dto);
            filmeGenero = service.salvar(filmeGenero);
            return new ResponseEntity(filmeGenero, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/lote")
    @ApiOperation("Salva vários vínculos Filme-Gênero de uma vez")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Vínculos salvos com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar algum vínculo")
    })
    public ResponseEntity<?> salvarLote(@RequestBody List<FilmeGeneroDTO> dtos) {
        try {
            List<FilmeGenero> vinculados = new ArrayList<>();

            for (FilmeGeneroDTO dto : dtos) {
                FilmeGenero fg = converter(dto); // método que busca o Filme e Gênero pelo ID
                vinculados.add(service.salvar(fg));
            }

            return new ResponseEntity<>(vinculados, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar um Filme Gênero")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Filme Gênero atualizado"),
            @ApiResponse(code = 400, message = "Erro ao atualizar Filme Gênero") })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody FilmeGeneroDTO dto) {
        if (!service.getFilmeGeneroById(id).isPresent()) {
            return new ResponseEntity("Filme Gênero não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            FilmeGenero filmeGenero = converter(dto);
            filmeGenero.setId(id);
            service.salvar(filmeGenero);
            return ResponseEntity.ok(filmeGenero);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um Filme Gênero")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Filme Gênero excluido com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir Filme Gênero") })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<FilmeGenero> filmeGenero = service.getFilmeGeneroById(id);
        if (!filmeGenero.isPresent()) {
            return new ResponseEntity("Filme gênero não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(filmeGenero.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public FilmeGenero converter(FilmeGeneroDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        FilmeGenero filmeGenero = modelMapper.map(dto, FilmeGenero.class);
        if (dto.getIdGenero() != null) {
            Optional<Genero> genero = generoService.getGeneroById(dto.getIdGenero());
            if (!genero.isPresent()) {
                filmeGenero.setGenero(null);
            } else {
                filmeGenero.setGenero(genero.get());
            }
        }
        if (dto.getIdFilme() != null) {
            Optional<Filme> filme = filmeService.getFilmeById(dto.getIdFilme());
            if (!filme.isPresent()) {
                filmeGenero.setFilme(null);
            } else {
                filmeGenero.setFilme(filme.get());
            }
        }
        return filmeGenero;
    }
}
