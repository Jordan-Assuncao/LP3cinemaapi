package com.example.cinemaapi.api.controller;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cinemaapi.api.dto.TipoExibicaoDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.TipoExibicao;
import com.example.cinemaapi.service.TipoExibicaoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/tipoexibicoes")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Tipo de Exibiçoes")
public class TipoExibicaoController {

    private final TipoExibicaoService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de Tipo de Exibições")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tipo de Exibições encontrados"),
            @ApiResponse(code = 404, message = "Tipo de Exibições não encontrados")})
    public ResponseEntity get() {
        List<TipoExibicao> tipoExibicaos = service.getTipoExibicaos();
        return ResponseEntity.ok(tipoExibicaos.stream().map(TipoExibicaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Tipo de Exibição")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tipo de Exibição encontrado"),
            @ApiResponse(code = 404, message = "Tipo de Exibição não encontrado")})
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TipoExibicao> tipoExibicao = service.getTipoExibicaoById(id);
        if (!tipoExibicao.isPresent()) {
            return new ResponseEntity("Tipo exibição não encontrada.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoExibicao.map(TipoExibicaoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo Tipo de Exibição")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Tipo de Exibição salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Tipo de Exibição")})
    public ResponseEntity post(@RequestBody TipoExibicaoDTO dto) {
        try {
            TipoExibicao tipoExibicao = converter(dto);
            tipoExibicao = service.salvar(tipoExibicao);
            return new ResponseEntity(tipoExibicao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
        @ApiOperation("Atualizar um Tipo de Exibição")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Tipo de exibição atualizado"),
            @ApiResponse(code = 400, message = "Erro ao atualizar Tipo de Exibição")})
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TipoExibicaoDTO dto) {
        if (!service.getTipoExibicaoById(id).isPresent()) {
            return new ResponseEntity("Tipo exibição não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            TipoExibicao tipoExibicao = converter(dto);
            tipoExibicao.setId(id);
            service.salvar(tipoExibicao);
            return ResponseEntity.ok(tipoExibicao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um Tipo de Exibição")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tipo de Exibiçao excluido com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir o Tipo de Exibição")})
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TipoExibicao> tipoExibicao = service.getTipoExibicaoById(id);
        if (!tipoExibicao.isPresent()) {
            return new ResponseEntity("Tipo exibição não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tipoExibicao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    

    public TipoExibicao converter(TipoExibicaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoExibicao tipoExibicao = modelMapper.map(dto, TipoExibicao.class);
        return tipoExibicao;
    }
}
