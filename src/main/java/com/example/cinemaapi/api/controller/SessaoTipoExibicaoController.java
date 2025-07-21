package com.example.cinemaapi.api.controller;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cinemaapi.api.dto.SessaoTipoExibicaoDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Sessao;
import com.example.cinemaapi.model.entity.SessaoTipoExibicao;
import com.example.cinemaapi.model.entity.TipoExibicao;
import com.example.cinemaapi.model.repository.SessaoTipoExibicaoRepository;
import com.example.cinemaapi.service.SessaoService;
import com.example.cinemaapi.service.SessaoTipoExibicaoService;
import com.example.cinemaapi.service.TipoExibicaoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/sessaotipoexibicoes")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Sessões Tipo de Exibições")
public class SessaoTipoExibicaoController {

    @Autowired
    private SessaoTipoExibicaoRepository repository;
    private final SessaoService sessaoService;
    private final TipoExibicaoService tipoExibicaoService;
    private final SessaoTipoExibicaoService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de Sessão Tipo de Exibição")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sessões tipo de exibições encontrados"),
            @ApiResponse(code = 404, message = "Sessões tipo de exibições não encontrados") })
    public ResponseEntity get() {
        List<SessaoTipoExibicao> sessaoTipoExibicaos = service.getSessaoTipoExibicaos();
        return ResponseEntity
                .ok(sessaoTipoExibicaos.stream().map(SessaoTipoExibicaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma Sessão Tipo de Exibição")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sessão Tipo de Exibição encontrado"),
            @ApiResponse(code = 404, message = "Sessão Tipo de Exibição não encontrado") })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<SessaoTipoExibicao> sessaoTipoExibicao = service.getSessaoTipoExibicaoById(id);
        if (!sessaoTipoExibicao.isPresent()) {
            return new ResponseEntity("Sessão tipo exibição não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sessaoTipoExibicao.map(SessaoTipoExibicaoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva uma nova Sessão Tipo de Exibição")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Sessão Tipo de Exibição salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a Sessão Tipo de Exibição") })
    public ResponseEntity post(@RequestBody SessaoTipoExibicaoDTO dto) {
        try {
            SessaoTipoExibicao sessaoTipoExibicao = converter(dto);
            sessaoTipoExibicao = service.salvar(sessaoTipoExibicao);
            return new ResponseEntity(sessaoTipoExibicao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/lote")
    @ApiOperation("Salva vários vínculos Sessão-Tipo de Exibição de uma vez")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Vínculos salvos com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar algum vínculo")
    })
    public ResponseEntity<?> salvarLote(@RequestBody List<SessaoTipoExibicaoDTO> dtos) {
        try {
            List<SessaoTipoExibicao> vinculados = new ArrayList<>();

            for (SessaoTipoExibicaoDTO dto : dtos) {
                SessaoTipoExibicao ste = converter(dto);
                vinculados.add(service.salvar(ste));
            }

            return new ResponseEntity<>(vinculados, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar uma Sessão Tipo de Exibição")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Sessão Tipo de Exibição atualizado"),
            @ApiResponse(code = 400, message = "Erro ao atualizar Sessão Tipo de Exibição") })
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
    @ApiOperation("Excluir uma Sessão Tipo de Exibição")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sessão Tipo de Exibição excluida com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir a Sessão Tipo de Exibição") })
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

    @GetMapping("/sessao/{sessaoId}")
    @ApiOperation("Buscar tipos de exibição vinculados a uma Sessão pelo ID da Sessão")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tipos de exibição encontrados"),
            @ApiResponse(code = 404, message = "Nenhum tipo de exibição encontrado para essa sessão") })
    public ResponseEntity<List<SessaoTipoExibicao>> buscarPorSessao(@PathVariable Long sessaoId) {
        List<SessaoTipoExibicao> lista = repository.findBySessaoId(sessaoId);
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/sessao/{id}")
    @ApiOperation("Deletar vínculos de tipos de exibição por ID da Sessão")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Vínculos deletados com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao deletar vínculos") })
    public ResponseEntity<Void> deletarVinculosPorSessao(@PathVariable Long id) {
        List<SessaoTipoExibicao> vinculos = repository.findBySessaoId(id);
        repository.deleteAll(vinculos);
        return ResponseEntity.noContent().build();
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
