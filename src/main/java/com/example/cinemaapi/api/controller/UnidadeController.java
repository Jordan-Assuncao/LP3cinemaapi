package com.example.cinemaapi.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.cinemaapi.api.dto.UnidadeDTO;
import com.example.cinemaapi.model.entity.Unidade;

@RestController
@RequestMapping("/api/v1/unidades")
@RequiredArgsConstructor
@CrossOrigin
public class UnidadeController {

    public Unidade converter(UnidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Unidade unidade = modelMapper.map(dto, Unidade.class);
        return unidade;
    }
}
