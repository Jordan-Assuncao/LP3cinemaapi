package com.example.cinemaapi.api.controller;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cinemaapi.api.dto.SalaDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Sala;
import com.example.cinemaapi.model.entity.Unidade;
import com.example.cinemaapi.service.SalaService;
import com.example.cinemaapi.service.UnidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/salas")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Salas")
public class SalaController {

    private final UnidadeService unidadeService;
    private final SalaService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de Salas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Salas encontradas"),
            @ApiResponse(code = 404, message = "Salas não encontradas")})
    public ResponseEntity get() {
        List<Sala> salas = service.getSalas();
        return ResponseEntity.ok(salas.stream().map(SalaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma Sala")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sala encontrada"),
            @ApiResponse(code = 404, message = "Sala não encontrada")})
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Sala> sala = service.getSalaById(id);
        if (!sala.isPresent()) {
            return new ResponseEntity("Sala não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sala.map(SalaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva uma nova Sala ")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Sala salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a Sala")})
    public ResponseEntity post(@RequestBody SalaDTO dto) {
        try {
            Sala sala = converter(dto);
            sala = service.salvar(sala);
            return new ResponseEntity(sala, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar uma Sala")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Sala atualizada"),
            @ApiResponse(code = 400, message = "Erro ao atualizar Sala")})
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody SalaDTO dto) {
        if (!service.getSalaById(id).isPresent()) {
            return new ResponseEntity("Sala não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Sala sala = converter(dto);
            sala.setId(id);
            service.salvar(sala);
            return ResponseEntity.ok(sala);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir uma Sala")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sala excluida com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir a Sala")})
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Sala> sala = service.getSalaById(id);
        if (!sala.isPresent()) {
            return new ResponseEntity("Sala não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(sala.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Sala converter(SalaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Sala sala = modelMapper.map(dto, Sala.class);
        if (dto.getIdUnidade() != null) {
            Optional<Unidade> unidade = unidadeService.getUnidadeById(dto.getIdUnidade());
            if (!unidade.isPresent()) {
                sala.setUnidade(null);
            } else {
                sala.setUnidade(unidade.get());
            }
        }
        return sala;
    }
}
