package com.example.cinemaapi.api.controller;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cinemaapi.api.dto.UnidadeDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Unidade;
import com.example.cinemaapi.service.UnidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/unidades")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Unidades")
public class UnidadeController {

    private final UnidadeService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de Unidades")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Unidades encontradas"),
            @ApiResponse(code = 404, message = "Unidades não encontradas")})
    public ResponseEntity get() {
        List<Unidade> unidades = service.getUnidades();
        return ResponseEntity.ok(unidades.stream().map(UnidadeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma Unidade")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Unidade encontrada"),
            @ApiResponse(code = 404, message = "Unidade não encontrada")})
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Unidade> unidade = service.getUnidadeById(id);
        if (!unidade.isPresent()) {
            return new ResponseEntity("Unidade não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(unidade.map(UnidadeDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva uma nova Unidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Unidade salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a Unidade")})
    public ResponseEntity post(@RequestBody UnidadeDTO dto) {
        try {
            Unidade unidade = converter(dto);
            unidade = service.salvar(unidade);
            return new ResponseEntity(unidade, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
        @ApiOperation("Atualizar uma Unidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Unidade atualizada"),
            @ApiResponse(code = 400, message = "Erro ao atualizar Unidade")})
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody UnidadeDTO dto) {
        if (!service.getUnidadeById(id).isPresent()) {
            return new ResponseEntity("Unidade não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Unidade unidade = converter(dto);
            unidade.setId(id);
            service.salvar(unidade);
            return ResponseEntity.ok(unidade);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir uma Unidade")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Unidade excluida com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir a Unidade")})
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Unidade> unidade = service.getUnidadeById(id);
        if (!unidade.isPresent()) {
            return new ResponseEntity("Unidade não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(unidade.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Unidade converter(UnidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Unidade unidade = modelMapper.map(dto, Unidade.class);
        return unidade;
    }
}
