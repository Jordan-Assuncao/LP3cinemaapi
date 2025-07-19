package com.example.cinemaapi.api.dto;

import com.example.cinemaapi.model.entity.CinemaAdmin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CinemaAdminDTO {
    private Long id;
    private String nomeCinema;
    private String email;
    private String senha;

    public static CinemaAdminDTO create(CinemaAdmin cinemaAdmin) {
        ModelMapper modelMapper = new ModelMapper();
        CinemaAdminDTO dto = modelMapper.map(cinemaAdmin, CinemaAdminDTO.class);
        return dto;
    }
}
