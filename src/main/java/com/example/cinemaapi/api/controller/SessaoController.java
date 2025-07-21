package com.example.cinemaapi.api.controller;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cinemaapi.api.dto.SessaoDTO;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Filme;
import com.example.cinemaapi.model.entity.Preco;
import com.example.cinemaapi.model.entity.Sala;
import com.example.cinemaapi.model.entity.Sessao;
import com.example.cinemaapi.model.entity.SessaoTipoExibicao;
import com.example.cinemaapi.model.repository.SessaoTipoExibicaoRepository;
import com.example.cinemaapi.service.FilmeService;
import com.example.cinemaapi.service.PrecoService;
import com.example.cinemaapi.service.SalaService;
import com.example.cinemaapi.service.SessaoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/sessoes")
@RequiredArgsConstructor
@CrossOrigin
@Api("API de Sessões")
public class SessaoController {

    @Autowired
    private SessaoTipoExibicaoRepository repository;

    private final FilmeService filmeService;
    private final PrecoService precoService;
    private final SalaService salaService;
    private final SessaoService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de Sessões")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sessões encontrados"),
            @ApiResponse(code = 404, message = "Sessões não encontradas") })
    public ResponseEntity get() {
        List<Sessao> sessaos = service.getSessaos();
        return ResponseEntity.ok(sessaos.stream().map(SessaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Sessão")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sessão encontrada"),
            @ApiResponse(code = 404, message = "Sessão não encontrada") })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Sessao> sessao = service.getSessaoById(id);
        if (!sessao.isPresent()) {
            return new ResponseEntity("Sessão não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sessao.map(SessaoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva uma nova Sessão")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Sessão salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a Sessão") })
    public ResponseEntity post(@RequestBody SessaoDTO dto) {
        try {
            Sessao sessao = converter(dto);
            sessao = service.salvar(sessao);
            return new ResponseEntity(sessao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar uma Sessão")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Sessão atualizada"),
            @ApiResponse(code = 400, message = "Erro ao atualizar Sessão") })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody SessaoDTO dto) {
        if (!service.getSessaoById(id).isPresent()) {
            return new ResponseEntity("Sessao não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Sessao sessao = converter(dto);
            sessao.setId(id);
            service.salvar(sessao);
            return ResponseEntity.ok(sessao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir uma Sessão")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sessão excluida com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir a Sessão") })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Sessao> sessao = service.getSessaoById(id);
        if (!sessao.isPresent()) {
            return new ResponseEntity("Sessão não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(sessao.get());
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

    @PostMapping("/lote")
    @ApiOperation("Salvar uma lista (lote) de vínculos Sessão-TipoExibicao")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Lista de vínculos salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar os vínculos") })
    public ResponseEntity<Void> salvarLote(@RequestBody List<SessaoTipoExibicao> lista) {
        repository.saveAll(lista);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/tiposexibicao/{id}")
    @ApiOperation("Deletar vínculos de tipos de exibição por ID da Sessão")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Vínculos deletados com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao deletar vínculos") })
    public ResponseEntity<Void> deletarVinculosPorSessao(@PathVariable Long id) {
        List<SessaoTipoExibicao> vinculos = repository.findBySessaoId(id);
        repository.deleteAll(vinculos);
        return ResponseEntity.noContent().build();
    }

    public Sessao converter(SessaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Sessao sessao = modelMapper.map(dto, Sessao.class);
        if (dto.getIdFilme() != null) {
            Optional<Filme> filme = filmeService.getFilmeById(dto.getIdFilme());
            if (!filme.isPresent()) {
                sessao.setFilme(null);
            } else {
                sessao.setFilme(filme.get());
            }
        }
        if (dto.getIdPreco() != null) {
            Optional<Preco> preco = precoService.getPrecoById(dto.getIdPreco());
            if (!preco.isPresent()) {
                sessao.setPreco(null);
            } else {
                sessao.setPreco(preco.get());
            }
        }
        if (dto.getIdSala() != null) {
            Optional<Sala> sala = salaService.getSalaById(dto.getIdSala());
            if (!sala.isPresent()) {
                sessao.setSala(null);
            } else {
                sessao.setSala(sala.get());
            }
        }
        return sessao;
    }
}
