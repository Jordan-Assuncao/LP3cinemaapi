package com.example.cinemaapi.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.cinemaapi.api.dto.TipoAssentoDTO;
import com.example.cinemaapi.model.entity.TipoAssento;

@RestController
@RequestMapping("/api/v1/tipoassentos")
@RequiredArgsConstructor
@CrossOrigin
public class TipoAssentoController {

    public TipoAssento converter(TipoAssentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoAssento tipoAssento = modelMapper.map(dto, TipoAssento.class);
        return tipoAssento;
    }
}
