package com.example.cinemaapi.api.dto;

import com.example.cinemaapi.model.entity.Genero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneroDTO {

    private Long id;
    private String nomeGenero;
    private String descricao;

    public static GeneroDTO create(Genero genero) {
        ModelMapper modelMapper = new ModelMapper();
        GeneroDTO dto = modelMapper.map(genero, GeneroDTO.class);
        return dto;
    }

}
