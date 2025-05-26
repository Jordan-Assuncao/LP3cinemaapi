package com.example.cinemaapi.api.dto;
import java.math.BigDecimal;

import com.example.cinemaapi.model.entity.Ingresso;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngressoDTO {

    private Long id;
    private boolean tipoIngresso;
    private Long idSessao;
    private String dataSessao;
    private String horaSessao;
    private Long idUnidade;
    private String nomeUnidade;
    private Long idSala;
    private String numeroSala;
    private Long idFilme;
    private String titulo;
    private Long idPreco;
    private BigDecimal valorInteira;
    private Long idAssento;
    private String numeroAssento;
    private Long idCompra;
    private String dataCompra;

    public static IngressoDTO create(Ingresso ingresso) {
        ModelMapper modelMapper = new ModelMapper();
        IngressoDTO dto = modelMapper.map(ingresso, IngressoDTO.class);
        dto.dataSessao = ingresso.getSessao().getDataSessao();
        dto.horaSessao = ingresso.getSessao().getHoraSessao();
        dto.idUnidade = ingresso.getSessao().getSala().getUnidade().getId();
        dto.nomeUnidade = ingresso.getSessao().getSala().getUnidade().getNomeUnidade();
        dto.idSala = ingresso.getSessao().getSala().getId();
        dto.numeroSala = ingresso.getSessao().getSala().getNumeroSala();
        dto.idFilme = ingresso.getSessao().getFilme().getId();
        dto.titulo = ingresso.getSessao().getFilme().getTitulo();
        dto.idPreco = ingresso.getSessao().getPreco().getId();
        dto.valorInteira = ingresso.getSessao().getPreco().getValorInteira();
        dto.numeroAssento = ingresso.getAssento().getNumeroAssento();
        dto.dataCompra = ingresso.getCompra().getDataCompra();
        return dto;
    }

}
