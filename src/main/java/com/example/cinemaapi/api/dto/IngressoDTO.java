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
    private String idSessao;
    private String dataSessao;
    private String horaSessao;
    private String nomeUnidade;
    private String numeroSala;
    private String idFilme;
    private String titulo;
    private String idPreco;
    private BigDecimal valorInteira;
    private String idAssento;
    private String numeroAssento;
    private String idCompra;
    private String dataCompra;

    public static IngressoDTO create(Ingresso ingresso) {
        ModelMapper modelMapper = new ModelMapper();
        IngressoDTO dto = modelMapper.map(ingresso, IngressoDTO.class);
        dto.dataSessao = ingresso.getSessao().getDataSessao();
        dto.horaSessao = ingresso.getSessao().getHoraSessao();
        dto.nomeUnidade = ingresso.getSessao().getSala().getUnidade().getNomeUnidade();
        dto.numeroSala = ingresso.getSessao().getSala().getNumeroSala();
        dto.titulo = ingresso.getSessao().getFilme().getTitulo();
        dto.valorInteira = ingresso.getSessao().getPreco().getValorInteira();
        dto.numeroAssento = ingresso.getAssento().getNumeroAssento();
        dto.dataCompra = ingresso.getCompra().getDataCompra();
        return dto;
    }

}
