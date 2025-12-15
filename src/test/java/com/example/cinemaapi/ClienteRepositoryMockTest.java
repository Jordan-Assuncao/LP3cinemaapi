package com.example.cinemaapi;
import com.example.cinemaapi.exception.RegraNegocioException;
import com.example.cinemaapi.model.entity.Cliente;
import com.example.cinemaapi.model.repository.ClienteRepository;
import com.example.cinemaapi.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)


class ClienteRepositoryMockTest {
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test

    void deveSalvarClienteQuandoDadosForemValidos() {
        Cliente cliente = clienteValido();

        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente resultado = clienteService.salvar(cliente);

        assertNotNull(resultado);
        assertEquals("Pedro", resultado.getNome());
        verify(clienteRepository, times(1)).save(cliente);
    }


    // CASO 2 — nome inválido

    @Test
    void deveLancarExcecaoQuandoNomeForInvalido() {
        Cliente cliente = clienteValido();
        cliente.setNome(null);

        RegraNegocioException excecao = assertThrows(
                RegraNegocioException.class,
                () -> clienteService.salvar(cliente)
        );

        assertEquals("Nome inválido", excecao.getMessage());
        verify(clienteRepository, never()).save(any());
    }


    // CASO 3 — buscar cliente por ID existente

    @Test
    void deveRetornarClienteQuandoIdExistir() {
        Cliente cliente = clienteValido();
        cliente.setId(1L);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.getClienteById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Pedro", resultado.get().getNome());
        verify(clienteRepository).findById(1L);
    }

    private Cliente clienteValido() {
        Cliente cliente = new Cliente();
        cliente.setNome("Pedro");
        cliente.setCpf("12345678900");
        cliente.setTelefone("32999999999");
        cliente.setEmail("pedro@email.com");
        cliente.setSenha("123456");
        return cliente;
    }

}
