package com.example.cinemaapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassificacaoIndicativaDTO {

    private Long id;
    private String faixaEtaria;
    private String descricao;


    public static ClassificacaoIndicativaDTO create(ClassificacaoIndicativaDTO classificacaoIndicativaDTO) {
        ModelMapper modelMapper = new ModelMapper();
        ClassificacaoIndicativaDTO dto = modelMapper.map(classificacaoIndicativaDTO, ClassificacaoIndicativaDTO.class);
        return dto;
    }
}
