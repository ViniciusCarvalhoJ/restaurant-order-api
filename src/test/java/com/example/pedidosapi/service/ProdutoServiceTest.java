package com.example.pedidosapi.service;

import com.example.pedidosapi.entity.Produto;
import com.example.pedidosapi.exception.ProdutoNaoEncontradoException;
import com.example.pedidosapi.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;
    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExiste() {

        when(produtoRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> {
            produtoService.buscarPorId(1L);
        });
    }

    @Test
    void deveRetornarProdutoQuandoExiste() {
        var produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setPreco(new BigDecimal("10.00"));
        produto.setEstoqueDisponivel(100);

        when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        var response = produtoService.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Produto Teste", response.getNome());
        assertEquals(new BigDecimal("10.00"), response.getPreco());
        assertEquals(100, response.getEstoqueDisponivel());
    }

    @Test
    void naoDeveRetornarProdutoDuplicado() {
        var produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Produto Teste");
        produto1.setPreco(new BigDecimal("10.00"));
        produto1.setEstoqueDisponivel(100);

        var produto2 = new Produto();
        produto2.setId(1L);
        produto2.setNome("Produto Teste");
        produto2.setPreco(new BigDecimal("10.00"));
        produto2.setEstoqueDisponivel(100);

        when(produtoRepository.findAll())
                .thenReturn(List.of(produto1, produto2));

        var produtos = produtoService.listarPorNome("Produto Teste");

        assertNotNull(produtos);
        assertEquals(2, produtos.size());
    }
}