package com.example.cinemaapi.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.cinemaapi.api.dto.PrecoDTO;
import com.example.cinemaapi.model.entity.Preco;

@RestController
@RequestMapping("/api/v1/precos")
@RequiredArgsConstructor
@CrossOrigin
public class PrecoController {

    public Preco converter(PrecoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Preco preco = modelMapper.map(dto, Preco.class);
        return preco;
    }
}
