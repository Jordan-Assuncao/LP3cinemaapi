package com.example.cinemaapi.api.controller;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cinemaapi.api.dto.SessaoTipoExibicaoDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Sessao;
import com.example.cinemaapi.model.entity.SessaoTipoExibicao;
import com.example.cinemaapi.model.entity.TipoExibicao;
import com.example.cinemaapi.service.SessaoService;
import com.example.cinemaapi.service.SessaoTipoExibicaoService;
import com.example.cinemaapi.service.TipoExibicaoService;

@RestController
@RequestMapping("/api/v1/sessaotipoexibicoes")
@RequiredArgsConstructor
@CrossOrigin
public class SessaoTipoExibicaoController {

    private final SessaoService sessaoService;
    private final TipoExibicaoService tipoExibicaoService;
    private final SessaoTipoExibicaoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<SessaoTipoExibicao> sessaoTipoExibicaos = service.getSessaoTipoExibicaos();
        return ResponseEntity.ok(sessaoTipoExibicaos.stream().map(SessaoTipoExibicaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<SessaoTipoExibicao> sessaoTipoExibicao = service.getSessaoTipoExibicaoById(id);
        if (!sessaoTipoExibicao.isPresent()) {
            return new ResponseEntity("Sessão tipo exibição não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sessaoTipoExibicao.map(SessaoTipoExibicaoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody SessaoTipoExibicaoDTO dto) {
        try {
            SessaoTipoExibicao sessaoTipoExibicao = converter(dto);
            sessaoTipoExibicao = service.salvar(sessaoTipoExibicao);
            return new ResponseEntity(sessaoTipoExibicao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody SessaoTipoExibicaoDTO dto) {
        if (!service.getSessaoTipoExibicaoById(id).isPresent()) {
            return new ResponseEntity("Sessão tipo de exibição não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            SessaoTipoExibicao sessaoTipoExibicao = converter(dto);
            sessaoTipoExibicao.setId(id);
            service.salvar(sessaoTipoExibicao);
            return ResponseEntity.ok(sessaoTipoExibicao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<SessaoTipoExibicao> sessaoTipoExibicao = service.getSessaoTipoExibicaoById(id);
        if (!sessaoTipoExibicao.isPresent()) {
            return new ResponseEntity("Sessão tipo exibição não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(sessaoTipoExibicao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public SessaoTipoExibicao converter(SessaoTipoExibicaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        SessaoTipoExibicao sessaoTipoExibicao = modelMapper.map(dto, SessaoTipoExibicao.class);
        if (dto.getIdSessao() != null) {
            Optional<Sessao> sessao = sessaoService.getSessaoById(dto.getIdSessao());
            if (!sessao.isPresent()) {
                sessaoTipoExibicao.setSessao(null);
            } else {
                sessaoTipoExibicao.setSessao(sessao.get());
            }
        }
        if (dto.getIdTipoExibicao() != null) {
            Optional<TipoExibicao> tipoExibicao = tipoExibicaoService.getTipoExibicaoById(dto.getIdTipoExibicao());
            if (!tipoExibicao.isPresent()) {
                sessaoTipoExibicao.setTipoExibicao(null);
            } else {
                sessaoTipoExibicao.setTipoExibicao(tipoExibicao.get());
            }
        }
        return sessaoTipoExibicao;
    }
}
