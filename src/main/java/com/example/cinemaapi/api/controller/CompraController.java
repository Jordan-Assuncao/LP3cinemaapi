package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.CompraDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.*;
import com.example.cinemaapi.service.ClienteService;
import com.example.cinemaapi.service.CompraService;

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
@RequestMapping("/api/v1/compras")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Compras")
public class CompraController {

    private final ClienteService clienteService;
    private final CompraService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de Compras")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Compras encontradas"),
            @ApiResponse(code = 404, message = "Compras não encontradas")})
    public ResponseEntity get() {
        List<Compra> compras = service.getCompras();
        return ResponseEntity.ok(compras.stream().map(CompraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma Compra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Compra encontrada"),
            @ApiResponse(code = 404, message = "Compra não encontrada")})
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Compra> compra = service.getCompraById(id);
        if (!compra.isPresent()) {
            return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(compra.map(CompraDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva uma nova Compra")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Compra salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a Compra")})
    public ResponseEntity post(@RequestBody CompraDTO dto) {
        try {
            Compra compra = converter(dto);
            compra = service.salvar(compra);
            return new ResponseEntity(compra, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
        @ApiOperation("Atualizar uma Compra")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Compra atualizada"),
            @ApiResponse(code = 400, message = "Erro ao atualizar a compra")})
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody CompraDTO dto) {
        if (!service.getCompraById(id).isPresent()) {
            return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Compra compra = converter(dto);
            compra.setId(id);
            service.salvar(compra);
            return ResponseEntity.ok(compra);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir uma Compra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Compra excluida com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir a Compra")})
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Compra> compra = service.getCompraById(id);
        if (!compra.isPresent()) {
            return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(compra.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Compra converter(CompraDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Compra compra = modelMapper.map(dto, Compra.class);
        if (dto.getIdCliente() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getIdCliente());
            if (!cliente.isPresent()) {
                compra.setCliente(null);
            } else {
                compra.setCliente(cliente.get());
            }
        }
        return compra;
    }
}
