package com.example.cinemaapi.api.dto;
import java.math.BigDecimal;

import com.example.cinemaapi.model.entity.Sessao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessaoDTO {

    private Long id;
    private String dataSessao;
    private String horaSessao;
    private boolean statusSessao;
    private boolean dublado;
    private boolean legendado;
    private Long idUnidade;
    private String nomeUnidade;
    private Long idSala;
    private String numeroSala;
    private Long idFilme;
    private String titulo;
    private Long idPreco;
    private BigDecimal valorInteira;

    public static SessaoDTO create(Sessao sessao) {
        ModelMapper modelMapper = new ModelMapper();
        SessaoDTO dto = modelMapper.map(sessao, SessaoDTO.class);
        dto.idUnidade = sessao.getSala().getUnidade().getId();
        dto.nomeUnidade = sessao.getSala().getUnidade().getNomeUnidade();
        dto.numeroSala = sessao.getSala().getNumeroSala();
        dto.titulo = sessao.getFilme().getTitulo();
        dto.valorInteira = sessao.getPreco().getValorInteira();
        return dto;
    }
}
