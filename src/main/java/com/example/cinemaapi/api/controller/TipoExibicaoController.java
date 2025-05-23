package com.example.cinemaapi.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.cinemaapi.api.dto.TipoExibicaoDTO;
import com.example.cinemaapi.model.entity.TipoExibicao;

@RestController
@RequestMapping("/api/v1/tipoexibicoes")
@RequiredArgsConstructor
@CrossOrigin
public class TipoExibicaoController {

    public TipoExibicao converter(TipoExibicaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoExibicao tipoExibicao = modelMapper.map(dto, TipoExibicao.class);
        return tipoExibicao;
    }
}
