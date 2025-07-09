package com.example.cinemaapi.api.controller;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cinemaapi.api.dto.PrecoDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Preco;
import com.example.cinemaapi.service.PrecoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/precos")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Preços")
public class PrecoController {

    private final PrecoService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de Preços")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Preços encontrados"),
            @ApiResponse(code = 404, message = "Preços não encontrados")})
    public ResponseEntity get() {
        List<Preco> precos = service.getPrecos();
        return ResponseEntity.ok(precos.stream().map(PrecoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Preço")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Preço encontrado"),
            @ApiResponse(code = 404, message = "Preço não encontrado")})
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Preco> preco = service.getPrecoById(id);
        if (!preco.isPresent()) {
            return new ResponseEntity("Preço não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(preco.map(PrecoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo Preço")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Preço salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Preço")})
    public ResponseEntity post(@RequestBody PrecoDTO dto) {
        try {
            Preco preco = converter(dto);
            preco = service.salvar(preco);
            return new ResponseEntity(preco, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar um Preço")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Preço atualizado"),
            @ApiResponse(code = 400, message = "Erro ao atualizar Preço")})
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody PrecoDTO dto) {
        if (!service.getPrecoById(id).isPresent()) {
            return new ResponseEntity("Preço não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Preco preco = converter(dto);
            preco.setId(id);
            service.salvar(preco);
            return ResponseEntity.ok(preco);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um Preço")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Preço excluido com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir Preço")})
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Preco> preco = service.getPrecoById(id);
        if (!preco.isPresent()) {
            return new ResponseEntity("Preço não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(preco.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Preco converter(PrecoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Preco preco = modelMapper.map(dto, Preco.class);
        return preco;
    }
}
