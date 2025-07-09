package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.GeneroDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Genero;
import com.example.cinemaapi.service.GeneroService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/generos")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Gêneros")
public class GeneroController {

    private final GeneroService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de Gêneros")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Gêneros encontrados"),
            @ApiResponse(code = 404, message = "Gêneros não encontrados")})
    public ResponseEntity get() {
        List<Genero> generos = service.getGeneros();
        return ResponseEntity.ok(generos.stream().map(GeneroDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Gênero")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Gênero encontrado"),
            @ApiResponse(code = 404, message = "Gênero não encontrado")})
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Genero> genero = service.getGeneroById(id);
        if (!genero.isPresent()) {
            return new ResponseEntity("Gênero não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(genero.map(GeneroDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo Gênero")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Gênero salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Gênero")})
    public ResponseEntity post(@RequestBody GeneroDTO dto) {
        try {
            Genero genero = converter(dto);
            genero = service.salvar(genero);
            return new ResponseEntity(genero, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar um Gênero")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Gênero atualizado"),
            @ApiResponse(code = 400, message = "Erro ao atualizar Gênero")})
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody GeneroDTO dto) {
        if (!service.getGeneroById(id).isPresent()) {
            return new ResponseEntity("Gênero não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Genero genero = converter(dto);
            genero.setId(id);
            service.salvar(genero);
            return ResponseEntity.ok(genero);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um Gênero")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Gênero excluido com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir Gênero")})
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Genero> genero = service.getGeneroById(id);
        if (!genero.isPresent()) {
            return new ResponseEntity("Gênero não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(genero.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Genero converter(GeneroDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Genero genero = modelMapper.map(dto, Genero.class);
        return genero;
    }
}
