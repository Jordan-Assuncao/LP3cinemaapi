package com.example.cinemaapi.api.dto;
import java.math.BigDecimal;

import com.example.cinemaapi.model.entity.Preco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrecoDTO {

    private Long id;
    private BigDecimal valorInteira;
    private String descricao;

    public static PrecoDTO create(Preco preco) {
        ModelMapper modelMapper = new ModelMapper();
        PrecoDTO dto = modelMapper.map(preco, PrecoDTO.class);
        return dto;
    }
}
