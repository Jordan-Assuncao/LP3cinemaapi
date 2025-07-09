package com.example.cinemaapi.api.controller;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cinemaapi.api.dto.TipoAssentoDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.TipoAssento;
import com.example.cinemaapi.service.TipoAssentoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/tipoassentos")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Tipo de Assentos")
public class TipoAssentoController {

    private final TipoAssentoService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de Tipos de Assento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tipos de Assento encontrados"),
            @ApiResponse(code = 404, message = "Tipos de Assento não encontrados")})
    public ResponseEntity get() {
        List<TipoAssento> tipoAssentos = service.getTipoAssentos();
        return ResponseEntity.ok(tipoAssentos.stream().map(TipoAssentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Tipo de Assento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tipo de Assento encontrado"),
            @ApiResponse(code = 404, message = "Tipo de Assento não encontrado")})
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TipoAssento> tipoAssento = service.getTipoAssentoById(id);
        if (!tipoAssento.isPresent()) {
            return new ResponseEntity("Tipo assento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoAssento.map(TipoAssentoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo Tipo de Assento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Tipo de Assento salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Tipo de Assento")})
    public ResponseEntity post(@RequestBody TipoAssentoDTO dto) {
        try {
            TipoAssento tipoAssento = converter(dto);
            tipoAssento = service.salvar(tipoAssento);
            return new ResponseEntity(tipoAssento, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar um Tipo de Assento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Tipo de Assento atualizado"),
            @ApiResponse(code = 400, message = "Erro ao atualizar Tipo de Assento")})
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TipoAssentoDTO dto) {
        if (!service.getTipoAssentoById(id).isPresent()) {
            return new ResponseEntity("Tipo Assento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TipoAssento tipoAssento = converter(dto);
            tipoAssento.setId(id);
            service.salvar(tipoAssento);
            return ResponseEntity.ok(tipoAssento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um Tipo de Assento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tipo de Assento excluido com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir o Tipo de Assento")})
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TipoAssento> tipoAssento = service.getTipoAssentoById(id);
        if (!tipoAssento.isPresent()) {
            return new ResponseEntity("Tipo Assento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tipoAssento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public TipoAssento converter(TipoAssentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoAssento tipoAssento = modelMapper.map(dto, TipoAssento.class);
        return tipoAssento;
    }
}
