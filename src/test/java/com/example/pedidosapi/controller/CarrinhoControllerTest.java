package com.example.pedidosapi.controller;

import com.example.pedidosapi.dto.PedidoDto;
import com.example.pedidosapi.entity.Carrinho;
import com.example.pedidosapi.entity.CarrinhoItem;
import com.example.pedidosapi.service.CarrinhoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarrinhoController.class)
class CarrinhoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarrinhoService carrinhoService;

    @Test
    void getCarrinho_deveRetornarCarrinho_quandoSucesso() throws Exception {
        // Arrange - Crie o carrinho de forma mais completa
        Carrinho carrinho = new Carrinho();
        carrinho.setId(1L);
        carrinho.setUsuarioId(1L);
        carrinho.setTotal(BigDecimal.valueOf(150.00));

        // Inicialize a lista de itens para evitar null
        carrinho.setItens(new ArrayList<>());

        CarrinhoItem item = new CarrinhoItem(null, 100L, "Produto Teste", BigDecimal.valueOf(99.90), 3);
        carrinho.getItens().add(item);

        when(carrinhoService.getOrCreateCart(anyLong())).thenReturn(carrinho);

        // Act + Assert
        mockMvc.perform(get("/carrinho")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.total").value(150.00));

        //verify(carrinhoService).getOrCreateCart(anyLong());
    }

    @Test
    void finalizarCarrinho_deveCriarPedido_quandoDadosValidos() throws Exception {
        // Arrange
        PedidoDto pedidoDto = new PedidoDto(); // preencha campos se necessário

        when(carrinhoService.criarPedido(anyLong(), anyString(), anyString()))
                .thenReturn(pedidoDto);

        String json = """
                {
                    "enderecoEntrega": "Rua A, 123",
                    "formaPagamento": "CARTAO"
                }
                """;

        // Act + Assert
        mockMvc.perform(post("/carrinho/finalizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(carrinhoService).criarPedido(anyLong(), anyString(), anyString());
    }
}