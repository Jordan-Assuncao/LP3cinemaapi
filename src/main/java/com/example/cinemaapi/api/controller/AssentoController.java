package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.AssentoDTO;
import com.example.cinemaapi.model.entity.Assento;
import com.example.cinemaapi.model.entity.Sala;
import com.example.cinemaapi.model.entity.TipoAssento;
import com.example.cinemaapi.service.SalaService;
import com.example.cinemaapi.service.TipoAssentoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/assentos")
@RequiredArgsConstructor
@CrossOrigin
public class AssentoController {

    private TipoAssentoService tipoAssentoService;
    private SalaService salaService;

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
