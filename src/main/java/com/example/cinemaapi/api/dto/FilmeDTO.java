package com.example.cinemaapi.api.dto;

import com.example.cinemaapi.model.entity.Filme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmeDTO {
    private Long id;

    private String titulo;
    private String sinopse;
    private String duracao;
    private String cartaz;
    private Long idClassificacaoIndicativa;
    private String faixaEtaria;
    private String descricao;

    public static FilmeDTO create(Filme filme) {
        ModelMapper modelMapper = new ModelMapper();
        FilmeDTO dto = modelMapper.map(filme, FilmeDTO.class);
        dto.faixaEtaria = filme.getClassificacaoIndicativa().getFaixaEtaria();
        dto.descricao = filme.getClassificacaoIndicativa().getDescricao();
        return dto;
    }
}
