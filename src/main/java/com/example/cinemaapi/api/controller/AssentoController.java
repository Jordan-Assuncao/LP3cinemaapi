package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.AssentoDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Assento;
import com.example.cinemaapi.model.entity.Sala;
import com.example.cinemaapi.model.entity.TipoAssento;
import com.example.cinemaapi.service.AssentoService;
import com.example.cinemaapi.service.SalaService;
import com.example.cinemaapi.service.TipoAssentoService;
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
@RequestMapping("/api/v1/assentos")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Assentos")
public class AssentoController {

    private final TipoAssentoService tipoAssentoService;
    private final SalaService salaService;
    private final AssentoService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de Assentos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Assentos encontrados"),
            @ApiResponse(code = 404, message = "Assentos não encontrados")})
    public ResponseEntity get() {
        List<Assento> assentos = service.getAssentos();
        return ResponseEntity.ok(assentos.stream().map(AssentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Assento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Assento encontrado"),
            @ApiResponse(code = 404, message = "Assento não encontrado")})
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Assento> assento = service.getAssentoById(id);
        if (!assento.isPresent()) {
            return new ResponseEntity("Assento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(assento.map(AssentoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo Assento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Assento salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o assento")})
    public ResponseEntity post(@RequestBody AssentoDTO dto) {
        try {
            Assento assento = converter(dto);
            assento = service.salvar(assento);
            return new ResponseEntity(assento, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar um Assento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Assento atualizado"),
            @ApiResponse(code = 400, message = "Erro ao atualizar assento")})
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AssentoDTO dto) {
        if (!service.getAssentoById(id).isPresent()) {
            return new ResponseEntity("Assento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Assento assento = converter(dto);
            assento.setId(id);
            service.salvar(assento);
            return ResponseEntity.ok(assento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um Assento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Assento excluido com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir Assento")})
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Assento> assento = service.getAssentoById(id);
        if (!assento.isPresent()) {
            return new ResponseEntity("Assento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(assento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Assento converter(AssentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Assento assento = modelMapper.map(dto, Assento.class);
        if (dto.getIdTipoAssento() != null) {
            Optional<TipoAssento> tipoAssento = tipoAssentoService.getTipoAssentoById(dto.getIdTipoAssento());
            if (!tipoAssento.isPresent()) {
                assento.setTipoAssento(null);
            } else {
                assento.setTipoAssento(tipoAssento.get());
            }
        }
        if (dto.getIdSala() != null) {
            Optional<Sala> sala = salaService.getSalaById(dto.getIdSala());
            if (!sala.isPresent()) {
                assento.setSala(null);
            } else {
                assento.setSala(sala.get());
            }
        }
        return assento;
    }
}
