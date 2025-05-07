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
    private String idUnidade;
    private String nomeUnidade;
    private String idSala;
    private String numeroSala;
    private String idFilme;
    private String titulo;
    private String idPreco;
    private BigDecimal valorInteira;

    public static SessaoDTO create(Sessao sessao) {
        ModelMapper modelMapper = new ModelMapper();
        SessaoDTO dto = modelMapper.map(sessao, SessaoDTO.class);
        dto.nomeUnidade = sessao.getSala().getUnidade().getNomeUnidade();
        dto.numeroSala = sessao.getSala().getNumeroSala();
        dto.titulo = sessao.getFilme().getTitulo();
        dto.valorInteira = sessao.getPreco().getValorInteira();
        return dto;
    }
}
