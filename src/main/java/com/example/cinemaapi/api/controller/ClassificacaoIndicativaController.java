package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.ClassificacaoIndicativaDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.ClassificacaoIndicativa;
import com.example.cinemaapi.service.ClassificacaoIndicativaService;

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
@RequestMapping("/api/v1/classificacaoindicativas")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Classificações Indicativas")
public class ClassificacaoIndicativaController {

    private final ClassificacaoIndicativaService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de Classificações Indicativas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Classificações Indicativas encontradas"),
            @ApiResponse(code = 404, message = "Classificações Indicativas não encontradas")})
    public ResponseEntity get() {
        List<ClassificacaoIndicativa> classificacaoIndicativas = service.getClassificacaoIndicativas();
        return ResponseEntity.ok(classificacaoIndicativas.stream().map(ClassificacaoIndicativaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma Classificação Indicativa")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Classificação Indicativa encontrada"),
            @ApiResponse(code = 404, message = "Classificação Indicativa não encontrada")})
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ClassificacaoIndicativa> classificacaoIndicativa = service.getClassificacaoIndicativaById(id);
        if (!classificacaoIndicativa.isPresent()) {
            return new ResponseEntity("Classificação indicativa não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(classificacaoIndicativa.map(ClassificacaoIndicativaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva uma nova Classificação Indicativa")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Classificação Indicativa salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a Classificação Indicativa")})
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
    @ApiOperation("Atualiza uma nova Classificação Indicativa")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Classificação Indicativa atualizada com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao atualizar a Classificação Indicativa")})
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

    @DeleteMapping("{id}")
    @ApiOperation("Excluir uma Classificação Indicativa")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Classificação Indicativa excluida com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir Classificação Indicativa")})
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ClassificacaoIndicativa> classificacaoIndicativa = service.getClassificacaoIndicativaById(id);
        if (!classificacaoIndicativa.isPresent()) {
            return new ResponseEntity("Classificação Indicativa não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(classificacaoIndicativa.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
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
