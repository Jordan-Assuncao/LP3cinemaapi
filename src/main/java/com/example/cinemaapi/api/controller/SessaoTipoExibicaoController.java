package com.example.cinemaapi.api.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.cinemaapi.api.dto.SessaoTipoExibicaoDTO;
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
