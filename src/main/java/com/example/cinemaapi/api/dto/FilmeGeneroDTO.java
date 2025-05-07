package com.example.cinemaapi.api.dto;

import com.example.cinemaapi.model.entity.FilmeGenero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmeGeneroDTO {

    private Long id;
    private String idFilme;
    private String titulo;
    private String idGenero;
    private String nomeGenero;

    public static FilmeGeneroDTO create(FilmeGenero filmeGenero) {
        ModelMapper modelMapper = new ModelMapper();
        FilmeGeneroDTO dto = modelMapper.map(filmeGenero, FilmeGeneroDTO.class);
        dto.titulo = filmeGenero.getFilme().getTitulo();
        dto.nomeGenero = filmeGenero.getGenero().getNomeGenero();
        return dto;
    }
}
