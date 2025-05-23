package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.CompraDTO;
import com.example.cinemaapi.model.entity.*;
import com.example.cinemaapi.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/compras")
@RequiredArgsConstructor
@CrossOrigin
public class CompraController {

    private final ClienteService clienteService;

    public Compra converter(CompraDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Compra compra = modelMapper.map(dto, Compra.class);
        if (dto.getIdCliente() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getIdCliente());
            if (!cliente.isPresent()) {
                compra.setCliente(null);
            } else {
                compra.setCliente(cliente.get());
            }
        }
        return compra;
    }
}
