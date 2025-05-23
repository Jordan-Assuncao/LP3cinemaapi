package com.example.cinemaapi.api.controller;

import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.cinemaapi.api.dto.SalaDTO;
import com.example.cinemaapi.model.entity.Sala;
import com.example.cinemaapi.model.entity.Unidade;
import com.example.cinemaapi.service.UnidadeService;

@RestController
@RequestMapping("/api/v1/salas")
@RequiredArgsConstructor
@CrossOrigin
public class SalaController {

    private final UnidadeService unidadeService;

    public Sala converter(SalaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Sala sala = modelMapper.map(dto, Sala.class);
        if (dto.getIdUnidade() != null) {
            Optional<Unidade> unidade = unidadeService.getUnidadeById(dto.getIdUnidade());
            if (!unidade.isPresent()) {
                sala.setUnidade(null);
            } else {
                sala.setUnidade(unidade.get());
            }
        }
        return sala;
    }
}
