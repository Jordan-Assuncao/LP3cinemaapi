package com.example.cinemaapi.api.dto;

import com.example.cinemaapi.model.entity.SessaoTipoExibicao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessaoTipoExibicaoDTO {

    private Long id;
    private Long idSessao;
    private String dataSessao;
    private String horaSessao;
    private Long idFilme;
    private String titulo;
    private Long idTipoExibicao;
    private String formatoExibicao;

    public static SessaoTipoExibicaoDTO create(SessaoTipoExibicao sessaoTipoExibicao) {
        ModelMapper modelMapper = new ModelMapper();
        SessaoTipoExibicaoDTO dto = modelMapper.map(sessaoTipoExibicao, SessaoTipoExibicaoDTO.class);
        dto.dataSessao = sessaoTipoExibicao.getSessao().getDataSessao();
        dto.horaSessao = sessaoTipoExibicao.getSessao().getHoraSessao();
        dto.titulo = sessaoTipoExibicao.getSessao().getFilme().getTitulo();
        dto.formatoExibicao = sessaoTipoExibicao.getTipoExibicao().getFormatoExibicao();
        return dto;
    }
}
