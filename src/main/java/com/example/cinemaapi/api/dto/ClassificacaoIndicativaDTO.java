package com.example.cinemaapi.api.dto;

import com.example.cinemaapi.model.entity.ClassificacaoIndicativa;
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


    public static ClassificacaoIndicativaDTO create(ClassificacaoIndicativa classificacaoIndicativa) {
        ModelMapper modelMapper = new ModelMapper();
        ClassificacaoIndicativaDTO dto = modelMapper.map(classificacaoIndicativa, ClassificacaoIndicativaDTO.class);
        return dto;
    }
}
