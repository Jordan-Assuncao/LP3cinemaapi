package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.FilmeDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.ClassificacaoIndicativa;
import com.example.cinemaapi.model.entity.Filme;
import com.example.cinemaapi.service.ClassificacaoIndicativaService;
import com.example.cinemaapi.service.FilmeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/filmes")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Filmes")
public class FilmeController {

    private final ClassificacaoIndicativaService classificacaoIndicativaService;
    private final FilmeService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de Filmes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Filmes encontrados"),
            @ApiResponse(code = 404, message = "Filmes não encontrados")})
    public ResponseEntity get() {
        List<Filme> filmes = service.getFilmes();
        return ResponseEntity.ok(filmes.stream().map(FilmeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Filme")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Filme encontrado"),
            @ApiResponse(code = 404, message = "Filme não encontrado")})
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Filme> filme = service.getFilmeById(id);
        if (!filme.isPresent()) {
            return new ResponseEntity("Filme não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(filme.map(FilmeDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo Filme")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Filme salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Filme")})
    public ResponseEntity post(@RequestBody FilmeDTO dto) {
        try {
            Filme filme = converter(dto);
            filme = service.salvar(filme);
            return new ResponseEntity(filme, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar um Filme")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Filme atualizado"),
            @ApiResponse(code = 400, message = "Erro ao atualizar Filme")})
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody FilmeDTO dto) {
        if (!service.getFilmeById(id).isPresent()) {
            return new ResponseEntity("Filme não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Filme filme = converter(dto);
            filme.setId(id);
            service.salvar(filme);
            return ResponseEntity.ok(filme);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um Filme")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Filme excluido com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir Filme")})
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Filme> filme = service.getFilmeById(id);
        if (!filme.isPresent()) {
            return new ResponseEntity("Filme não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(filme.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Filme converter(FilmeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Filme filme = modelMapper.map(dto, Filme.class);
        if (dto.getIdClassificacaoIndicativa() != null) {
            Optional<ClassificacaoIndicativa> classificacaoIndicativa = classificacaoIndicativaService
                    .getClassificacaoIndicativaById(dto.getIdClassificacaoIndicativa());
            if (!classificacaoIndicativa.isPresent()) {
                filme.setClassificacaoIndicativa(null);
            } else {
                filme.setClassificacaoIndicativa(classificacaoIndicativa.get());
            }
        }
        return filme;
    }
}
