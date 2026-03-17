package com.example.pedidosapi.dto;


import com.example.pedidosapi.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponse {

    private Long id;
    private String nome;
    private BigDecimal preco;
    private Integer estoqueDisponivel;

    public static ProdutoResponse from(Produto produto) {
        ProdutoResponse response = new ProdutoResponse();
        response.setId(produto.getId());
        response.setNome(produto.getNome());
        response.setPreco(produto.getPreco());
        response.setEstoqueDisponivel(produto.getEstoqueDisponivel());
        return response;

    }
}
