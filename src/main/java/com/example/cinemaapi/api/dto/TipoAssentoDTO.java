package com.example.cinemaapi.api.dto;

import com.example.cinemaapi.model.entity.TipoAssento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoAssentoDTO {

    private Long id;
    private String nomeAssento;
    private String descricao;

    public static TipoAssentoDTO create(TipoAssento tipoAssento) {
        ModelMapper modelMapper = new ModelMapper();
        TipoAssentoDTO dto = modelMapper.map(tipoAssento, TipoAssentoDTO.class);
        return dto;
    }
}
