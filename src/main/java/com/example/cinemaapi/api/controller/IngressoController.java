package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.IngressoDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Assento;
import com.example.cinemaapi.model.entity.Ingresso;
import com.example.cinemaapi.model.entity.Sessao;
import com.example.cinemaapi.model.entity.Compra;
import com.example.cinemaapi.service.AssentoService;
import com.example.cinemaapi.service.SessaoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.example.cinemaapi.service.CompraService;
import com.example.cinemaapi.service.IngressoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ingressos")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Ingressos")
public class IngressoController {

    private final SessaoService sessaoService;
    private final AssentoService assentoService;
    private final CompraService compraService;
    private final IngressoService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de Ingressos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ingressos encontrados"),
            @ApiResponse(code = 404, message = "Ingressos não encontrados")})
    public ResponseEntity get() {
        List<Ingresso> ingressos = service.getIngressos();
        return ResponseEntity.ok(ingressos.stream().map(IngressoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Ingresso")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ingresso encontrado"),
            @ApiResponse(code = 404, message = "Ingresso não encontrado")})
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Ingresso> ingresso = service.getIngressoById(id);
        if (!ingresso.isPresent()) {
            return new ResponseEntity("Ingresso não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(ingresso.map(IngressoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo Ingresso")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Ingresso salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar Ingresso")})
    public ResponseEntity post(@RequestBody IngressoDTO dto) {
        try {
            Ingresso ingresso = converter(dto);
            ingresso = service.salvar(ingresso);
            return new ResponseEntity(ingresso, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar um Ingresso")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Ingresso atualizado"),
            @ApiResponse(code = 400, message = "Erro ao atualizar Ingresso")})
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody IngressoDTO dto) {
        if (!service.getIngressoById(id).isPresent()) {
            return new ResponseEntity("Ingresso não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Ingresso ingresso = converter(dto);
            ingresso.setId(id);
            service.salvar(ingresso);
            return ResponseEntity.ok(ingresso);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um Ingresso")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ingresso excluido com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir Ingresso")})
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Ingresso> ingresso = service.getIngressoById(id);
        if (!ingresso.isPresent()) {
            return new ResponseEntity("Ingresso não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(ingresso.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Ingresso converter(IngressoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Ingresso ingresso = modelMapper.map(dto, Ingresso.class);
        if (dto.getIdSessao() != null) {
            Optional<Sessao> sessao = sessaoService.getSessaoById(dto.getIdSessao());
            if (!sessao.isPresent()) {
                ingresso.setSessao(null);
            } else {
                ingresso.setSessao(sessao.get());
            }
        }
        if (dto.getIdAssento() != null) {
            Optional<Assento> assento = assentoService.getAssentoById(dto.getIdAssento());
            if (!assento.isPresent()) {
                ingresso.setAssento(null);
            } else {
                ingresso.setAssento(assento.get());
            }
        }
        if (dto.getIdCompra() != null) {
            Optional<Compra> compra = compraService.getCompraById(dto.getIdCompra());
            if (!compra.isPresent()) {
                ingresso.setCompra(null);
            } else {
                ingresso.setCompra(compra.get());
            }
        }
        return ingresso;
    }
}
