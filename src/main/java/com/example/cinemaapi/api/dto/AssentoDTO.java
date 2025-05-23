package com.example.cinemaapi.api.dto;

import com.example.cinemaapi.model.entity.Assento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssentoDTO {

    private Long id;
    private String numeroAssento;
    private int fileiraVertical;
    private int fileiraHorizontal;
    private boolean statusAssento;
    private Long idUnidade;
    private String nomeUnidade;
    private Long idSala;
    private String numeroSala;
    private Long idTipoAssento;
    private String nomeAssento;

    public static AssentoDTO create(Assento assento) {
        ModelMapper modelMapper = new ModelMapper();
        AssentoDTO dto = modelMapper.map(assento, AssentoDTO.class);
        dto.nomeUnidade = assento.getSala().getUnidade().getNomeUnidade();
        dto.numeroSala = assento.getSala().getNumeroSala();
        dto.nomeAssento = assento.getTipoAssento().getNomeAssento();
        return dto;
    }
}
