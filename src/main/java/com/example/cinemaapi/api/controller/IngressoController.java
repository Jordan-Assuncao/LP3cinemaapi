package com.example.cinemaapi.api.controller;

import com.example.cinemaapi.api.dto.IngressoDTO;
import com.example.cinemaapi.model.entity.Assento;
import com.example.cinemaapi.model.entity.Ingresso;
import com.example.cinemaapi.model.entity.Sessao;
import com.example.cinemaapi.model.entity.Compra;
import com.example.cinemaapi.service.AssentoService;
import com.example.cinemaapi.service.SessaoService;
import com.example.cinemaapi.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ingressos")
@RequiredArgsConstructor
@CrossOrigin
public class IngressoController {

    private final SessaoService sessaoService;
    private final AssentoService assentoService;
    private final CompraService compraService;

    public Ingresso converter(IngressoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Ingresso ingresso = modelMapper.map(dto, Ingresso.class);
        if (dto.getIdSessao() != null) {
            Optional<Sessao> sessao = sessaoService.getSessaoById(dto.getIdSessao());
            if (!sessao.isPresent()) {
                ingresso.setSessao(null);
            } else {
                ingresso.setSessao(sessao.get());
            }
        }
        if (dto.getIdAssento() != null) {
            Optional<Assento> assento = assentoService.getAssentoById(dto.getIdAssento());
            if (!assento.isPresent()) {
                ingresso.setAssento(null);
            } else {
                ingresso.setAssento(assento.get());
            }
        }
        if (dto.getIdCompra() != null) {
            Optional<Compra> compra = compraService.getCompraById(dto.getIdCompra());
            if (!compra.isPresent()) {
                ingresso.setCompra(null);
            } else {
                ingresso.setCompra(compra.get());
            }
        }
        return ingresso;
    }
}
