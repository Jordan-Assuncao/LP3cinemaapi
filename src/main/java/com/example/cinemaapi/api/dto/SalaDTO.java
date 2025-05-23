package com.example.cinemaapi.api.dto;

import com.example.cinemaapi.model.entity.Sala;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaDTO {

    private Long id;
    private String numeroSala;
    private int capacidade;
    private int capacidadePreferencial;
    private String formatoSala;
    private int numeroFileiraVertical;
    private int numeroFileiraHorizontal;
    private Long idUnidade;
    private String nomeUnidade;

    public static SalaDTO create(Sala sala) {
        ModelMapper modelMapper = new ModelMapper();
        SalaDTO dto = modelMapper.map(sala, SalaDTO.class);
        dto.nomeUnidade = sala.getUnidade().getNomeUnidade();
        return dto;
    }
}
