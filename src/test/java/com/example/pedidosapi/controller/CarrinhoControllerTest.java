package com.example.pedidosapi.controller;

import com.example.pedidosapi.dto.PedidoDto;
import com.example.pedidosapi.entity.Carrinho;
import com.example.pedidosapi.repository.CarrinhoRepository;
import com.example.pedidosapi.repository.PedidoRepository;
import com.example.pedidosapi.repository.ProdutoRepository;
import com.example.pedidosapi.service.CarrinhoService;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CarrinhoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarrinhoService carrinhoService;

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private CarrinhoService service;




    @Test
    void getCarrinho_deveRetornarCarrinho_quandoSucesso() throws Exception {

        // ======================
        // ARRANGE (preparar)
        // ======================
        Carrinho carrinho = new Carrinho();

        when(carrinhoService.getOrCreateCart(anyLong()))
                .thenReturn(carrinho);

        // ======================
        // ACT (executar)
        // ======================
        mockMvc.perform(get("/carrinho"))

                // ======================
                // ASSERT (validar)
                // ======================
                .andExpect(status().isOk());

        // verifica se o service foi chamado
        verify(carrinhoService).getOrCreateCart(anyLong());
    }

    @Test
    void finalizarCarrinho_deveCriarPedido_quandoDadosValidos() throws Exception {

        // ARRANGE
        PedidoDto pedido = new PedidoDto();

        when(carrinhoService.criarPedido(anyLong(), anyString(), any()))
                .thenReturn(pedido);

        String json = """
        {
            "enderecoEntrega": "Rua A",
            "formaPagamento": "CARTAO"
        }
    """;

        // ACT + ASSERT
        mockMvc.perform(post("/carrinho/finalizar")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(json))
                .andExpect(status().isCreated());

        verify(carrinhoService)
                .criarPedido(anyLong(), anyString(), any());
    }
}