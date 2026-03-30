package com.example.pedidosapi.service;

import com.example.pedidosapi.entity.Carrinho;
import com.example.pedidosapi.repository.CarrinhoRepository;
import com.example.pedidosapi.repository.PedidoRepository;
import com.example.pedidosapi.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarrinhoServiceTest {

    @InjectMocks
    private CarrinhoService carrinhoService;

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getOrCreateCart_deveRetornarCarrinhoExistente_quandoCarrinhoJaExiste() {
        // Arrange
        Long usuarioId = 1L;
        Carrinho carrinhoExistente = new Carrinho();
        carrinhoExistente.setUsuarioId(usuarioId);
        carrinhoExistente.setTotal(BigDecimal.TEN);

        when(carrinhoRepository.findByUsuarioId(usuarioId)).thenReturn(Optional.of(carrinhoExistente));

        // Act
        Carrinho resultado = carrinhoService.getOrCreateCart(usuarioId);

        // Assert
        assertEquals(carrinhoExistente, resultado);
        verify(carrinhoRepository, times(1)).findByUsuarioId(usuarioId);
        verify(carrinhoRepository, never()).save(any(Carrinho.class));
    }

    @Test
    void getOrCreateCart_deveCriarNovoCarrinho_quandoCarrinhoNaoExiste() {
        // Arrange
        Long usuarioId = 1L;
        when(carrinhoRepository.findByUsuarioId(usuarioId)).thenReturn(Optional.empty());
        when(carrinhoRepository.save(any(Carrinho.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Carrinho resultado = carrinhoService.getOrCreateCart(usuarioId);

        // Assert
        assertNotNull(resultado);
        assertEquals(usuarioId, resultado.getUsuarioId());
        assertEquals(BigDecimal.ZERO, resultado.getTotal());
        verify(carrinhoRepository, times(1)).findByUsuarioId(usuarioId);
        verify(carrinhoRepository, times(1)).save(any(Carrinho.class));
    }
}
