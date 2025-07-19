package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.CinemaAdminDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.CinemaAdmin;
import com.example.cinemaapi.service.CinemaAdminService;
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
@RequestMapping("/api/v1/cinemaadmins")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Admins")
public class CinemaAdminController {
    private final CinemaAdminService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de Admins")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Admins encontrados"),
            @ApiResponse(code = 404, message = "Admins não encontrados")})
    public ResponseEntity get() {
        List<CinemaAdmin> cinemaAdmin = service.getCinemaAdmins();
        return ResponseEntity.ok(cinemaAdmin.stream().map(CinemaAdminDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Admin")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Admin encontrado"),
            @ApiResponse(code = 404, message = "Admin não encontrado")})
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<CinemaAdmin> cinemaAdmin = service.getCinemaAdminById(id);
        if (!cinemaAdmin.isPresent()) {
            return new ResponseEntity("Admin não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cinemaAdmin.map(CinemaAdminDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo Admin")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Admin salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o Admin")})
    public ResponseEntity post(@RequestBody CinemaAdminDTO dto) {
        try {
            CinemaAdmin cinemaAdmin = converter(dto);
            cinemaAdmin = service.salvar(cinemaAdmin);
            return new ResponseEntity(cinemaAdmin, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar um Admin")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Admin atualizado"),
            @ApiResponse(code = 400, message = "Erro ao atualizar Admin")})
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody CinemaAdminDTO dto) {
        if (!service.getCinemaAdminById(id).isPresent()) {
            return new ResponseEntity("Admin não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            CinemaAdmin cinemaAdmin = converter(dto);
            cinemaAdmin.setId(id);
            service.salvar(cinemaAdmin);
            return ResponseEntity.ok(cinemaAdmin);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um Admin")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Admin excluido com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir Admin")})
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<CinemaAdmin> cinemaAdmin = service.getCinemaAdminById(id);
        if (!cinemaAdmin.isPresent()) {
            return new ResponseEntity("Admin não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(cinemaAdmin.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public CinemaAdmin converter(CinemaAdminDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        CinemaAdmin cinemaAdmin = modelMapper.map(dto, CinemaAdmin.class);
        return cinemaAdmin;
    }
}
