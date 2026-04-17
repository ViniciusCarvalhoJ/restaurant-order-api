package com.example.pedidosapi.service;

import com.example.pedidosapi.entity.Pedido;
import com.example.pedidosapi.entity.StatusPedido;
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

    private Pedido pedido;

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pedido = new Pedido();
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


    @Test
    void atualizarDescricao_deveAtualizarDescricaoDoPedido() {
        // Quando
        Long pedidoId = 1L;
        Pedido pedido = new Pedido();

        pedido.setDescricao("Pizza");

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));

        pedidoService.atualizarDescricao(pedidoId, "Hamburguer");

        assertEquals("Hamburguer", pedido.getDescricao());
    }

    // ==================== HAPPY PATH ====================

    @Test
    void deveCriarPedidoNoEstadoCriado() {
        assertEquals(StatusPedido.CRIADO, pedido.getStatus());  // assumindo que tem getter
    }

    @Test
    void devePermitirPagarPedidoCriado() {
        pedido.pagar();
        assertEquals(StatusPedido.PAGO, pedido.getStatus());
    }

    @Test
    void devePermitirFluxoCompletoAteEntregue() {
        pedido.pagar();
        pedido.iniciarPreparacao();
        pedido.enviar();
        pedido.entregar();

        assertEquals(StatusPedido.ENTREGUE, pedido.getStatus());
    }

    @Test
    void devePermitirCancelarEmQualquerEstadoExcetoEntregue() {
        pedido.pagar();
        pedido.cancelar();
        assertEquals(StatusPedido.CANCELADO, pedido.getStatus());
    }

    // ==================== CENÁRIOS DE ERRO ====================

    @Test
    void naoDevePagarPedidoQueJaFoiPago() {
        pedido.pagar();  // agora está PAGO

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> pedido.pagar());

        assertEquals("Pedido nao pode ser pago.", exception.getMessage());
    }

    @Test
    void naoDeveIniciarPreparacaoSemPagarAntes() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> pedido.iniciarPreparacao());

        assertEquals("Pedido nao pode iniciser preparado.", exception.getMessage()); // note o typo no seu código
    }

    @Test
    void naoDeveCancelarPedidoJaEntregue() {
        pedido.pagar();
        pedido.iniciarPreparacao();
        pedido.enviar();
        pedido.entregar();

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> pedido.cancelar());

        assertEquals("Pedido nao pode ser cancelado.", exception.getMessage());
    }


}

