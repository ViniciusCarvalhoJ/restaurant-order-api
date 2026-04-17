package com.example.pedidosapi.service;

import com.example.pedidosapi.entity.Carrinho;
import com.example.pedidosapi.repository.CarrinhoRepository;
import com.example.pedidosapi.repository.PedidoRepository;
import com.example.pedidosapi.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarrinhoServiceTest {

    @InjectMocks
    private CarrinhoService carrinhoService;

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private PedidoRepository pedidoRepository;


    void validarCarrinho(Long usuarioId, Optional<Carrinho> carrinhoExistente, boolean deveCriarNovo) {

        // Arrange
        when(carrinhoRepository.findByUsuarioId(usuarioId))
                .thenReturn(carrinhoExistente);

        if (deveCriarNovo) {
            when(carrinhoRepository.save(any(Carrinho.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));
        }

        // Act
        Carrinho resultado = carrinhoService.getOrCreateCart(usuarioId);

        // Assert
        if (deveCriarNovo) {
            assertNotNull(resultado);
            assertEquals(usuarioId, resultado.getUsuarioId());
            assertEquals(BigDecimal.ZERO, resultado.getTotal());
            verify(carrinhoRepository, times(1))
                    .save(any(Carrinho.class));
        } else {
            assertTrue(carrinhoExistente.isPresent());
            assertEquals(carrinhoExistente.get(), resultado);
            verify(carrinhoRepository, never())
                    .save(any(Carrinho.class));
        }

        verify(carrinhoRepository, times(1)).findByUsuarioId(usuarioId);
    }

    @Test
    void getOrCreateCart_deveRetornarCarrinhoExistente_quandoCarrinhoJaExiste() {
        Long usuarioId = 1L;
        Carrinho carrinhoExistente = new Carrinho();
        carrinhoExistente.setUsuarioId(usuarioId);
        carrinhoExistente.setTotal(BigDecimal.TEN);

        validarCarrinho(usuarioId, Optional.of(carrinhoExistente), false);
    }

    @Test
    void getOrCreateCart_deveCriarNovoCarrinho_quandoCarrinhoInexistente() {
        Long usuarioId = 1L;
        validarCarrinho(usuarioId, Optional.empty(), true);
    }
}
