package com.example.pedidosapi.service;

import com.example.pedidosapi.entity.Pedido;
import com.example.pedidosapi.exception.PedidoNaoEncontradoException;
import com.example.pedidosapi.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarPedido_deveRetornarPedido_quandoPedidoExiste() {
        // Arrange
        Long pedidoId = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(pedidoId);

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));

        // Act
        Pedido resultado = pedidoService.buscarPedido(pedidoId);

        // Assert
        assertEquals(pedido, resultado);
        verify(pedidoRepository, times(1)).findById(pedidoId);
    }

    @Test
    void buscarPedido_deveLancarExcecao_quandoPedidoNaoExiste() {
        // Arrange
        Long pedidoId = 1L;
        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PedidoNaoEncontradoException.class, () -> pedidoService.buscarPedido(pedidoId));
        verify(pedidoRepository, times(1)).findById(pedidoId);
    }
}
