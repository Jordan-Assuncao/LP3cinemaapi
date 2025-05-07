package com.example.cinemaapi.api.dto;

import com.example.cinemaapi.model.entity.TipoExibicao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoExibicaoDTO {

    private Long id;
    private String formatoExibicao;
    private String descricao;
    private boolean formatoUnico;

    public static TipoExibicaoDTO create(TipoExibicao tipoExibicao) {
        ModelMapper modelMapper = new ModelMapper();
        TipoExibicaoDTO dto = modelMapper.map(tipoExibicao, TipoExibicaoDTO.class);
        return dto;
    }
}
