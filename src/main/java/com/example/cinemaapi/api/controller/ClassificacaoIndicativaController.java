package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.ClassificacaoIndicativaDTO;
import com.example.cinemaapi.model.entity.ClassificacaoIndicativa;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/classificacaoindicativas")
@RequiredArgsConstructor
@CrossOrigin
public class ClassificacaoIndicativaController {

    public ClassificacaoIndicativa converter(ClassificacaoIndicativaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ClassificacaoIndicativa classificacaoIndicativa = modelMapper.map(dto, ClassificacaoIndicativa.class);
        return classificacaoIndicativa;
    }
}
