package com.example.cinemaapi.api.dto;

import com.example.cinemaapi.model.entity.Unidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeDTO {
    private Long id;

    private String nomeUnidade;
    private String cnpj;
    private String email;
    private String telefone;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public static UnidadeDTO create(Unidade unidade) {
        ModelMapper modelMapper = new ModelMapper();
        UnidadeDTO dto = modelMapper.map(unidade, UnidadeDTO.class);
        return dto;
    }
}
