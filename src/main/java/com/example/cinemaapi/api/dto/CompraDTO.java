package com.example.cinemaapi.api.dto;
import java.math.BigDecimal;

import com.example.cinemaapi.model.entity.Compra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraDTO {

    private Long id;
    private String dataCompra;
    private BigDecimal valorTotal;
    private Long idCliente;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

        public static CompraDTO create(Compra compra) {
        ModelMapper modelMapper = new ModelMapper();
        CompraDTO dto = modelMapper.map(compra, CompraDTO.class);
        dto.nome = compra.getCliente().getNome();
        dto.cpf = compra.getCliente().getCpf();
        dto.telefone = compra.getCliente().getTelefone();
        dto.email = compra.getCliente().getEmail();
        return dto;
    }

}
