package com.example.pedidosapi.controller;

import com.example.pedidosapi.dto.ProdutoResponse;
import com.example.pedidosapi.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProdutoService produtoService;

    @Test
    void listarProdutos_deveRetornarListaDeProdutos_quandoExistemProdutos() throws Exception {
        // Arrange
        ProdutoResponse produto1 = new ProdutoResponse(1L, "Produto A", BigDecimal.TEN, 100);
        ProdutoResponse produto2 = new ProdutoResponse(2L, "Produto B", BigDecimal.ONE, 50);

        when(produtoService.listaTodos()).thenReturn(Arrays.asList(produto1, produto2));

        // Act & Assert
        mockMvc.perform(get("/produtos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Produto A"))
                .andExpect(jsonPath("$[1].nome").value("Produto B"));
    }

    @Test
    void listarProdutos_deveRetornarListaVazia_quandoNaoExistemProdutos() throws Exception {
        // Arrange
        when(produtoService.listaTodos()).thenReturn(Arrays.asList());

        // Act & Assert
        mockMvc.perform(get("/produtos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
